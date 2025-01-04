package cn.rx.core.exception.handler;

import cn.rx.common.constant.HeaderConstant;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.util.HttpUtil;
import cn.rx.common.util.IpUtil;
import cn.rx.common.vo.sysLogin.SysLoginAdminVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysOplog;
import cn.rx.db.mapper.SysOplogMapper;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandling {

    @Resource
    private MessageSource messageSource;

    @Resource
    SysOplogMapper sysOplogMapper;

    @Resource
    Ip2regionSearcher ip2regionSearcher;

    /**
     * 没有捕捉到的异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<?> doException(Exception ex) {
        log.error("未知异常：",ex);
        setLog(LogLeve.GENERAL,LogModel.SECURE,ex,"系统错误,请联系管理员");
        return Result.error(R.ERROR_EXCEPTION, handlerMsg(R.ERROR_EXCEPTION.msg));
    }

    //业务异常处理
    @ExceptionHandler(value = {BusinessException.class})
    public Result<?> doBusinessException(BusinessException ex) {
        log.error("业务异常：",ex);
        return Result.error(ex.getCode(), handlerMsg(ex.getMessage()));
    }

    //shiro权限校验失败
    @ExceptionHandler(value = {UnauthorizedException.class})
    public Result<?> doBusinessException(UnauthorizedException ex) {
        log.error("权限校验失败",ex);
        setLog(LogLeve.GENERAL,LogModel.SECURE,ex,"权限校验失败");
        return Result.error(R.ERROR_PERMISSION,handlerMsg(R.ERROR_PERMISSION.msg));
    }



    //不加@RequestBody注解的校验失败抛BindException异常
    @ExceptionHandler(value = {BindException.class})
    public Result<?> doBusinessException(BindException ex) {
        log.error("RequestParam注解校验失败",ex);
        List<String> msg = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return Result.error(R.ERROR_QUERY, handlerMsg(msg));
    }


    /**
     * 添加@RequestBody注解的校验失败抛MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(BindException ex) {
        log.error("RequestBody注解校验失败",ex);
        List<String> msg = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return Result.error(R.ERROR_QUERY, handlerMsg(msg));
    }


    /**
     * 基本数据类型添加@NotBlank等注解校验失败抛ConstraintViolationException异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("基本数据类型注解的校验失败",ex);
        List<String> msg = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate).collect(Collectors.toList());
        return Result.error(R.ERROR_QUERY, handlerMsg(msg));
    }


    /**
     * 错误日志记录
     *
     * @param leve        级别
     * @param model       模块
     */
    public void setLog(LogLeve leve, LogModel model, Object result,String description) {
        String ipAddress = IpUtil.getIpAddress(HttpUtil.getHttpInfo());
        String address = ip2regionSearcher.getAddress(ipAddress);
        SysOplog sysOplog = new SysOplog();
        sysOplog.setTimes(10);
        sysOplog.setIp(ipAddress);
        sysOplog.setAddress(address);
        sysOplog.setDescription(description);
        sysOplog.setModel(model.value);
        if(result instanceof  String){
            sysOplog.setResult((String) result);
        }else if(result instanceof  Exception){
            Map<String,Object> stringObjectMap = new HashMap<>();
            Exception ex = (Exception)result;
            stringObjectMap.put("message",ex.getMessage());
            stringObjectMap.put("localizedMessage",ex.getLocalizedMessage());
            stringObjectMap.put("type",ex.getClass());
            sysOplog.setResult(JSON.toJSONString(stringObjectMap));
        }else {
            sysOplog.setResult(JSON.toJSONString(result));
        }
        sysOplog.setCategory(leve.value);
        Claims userLoginInfo = HttpUtil.getUserLoginInfo();
        if (Objects.nonNull(userLoginInfo)) {
            sysOplog.setUserId(Long.valueOf(userLoginInfo.getSubject()));
        }
        sysOplogMapper.insert(sysOplog);
    }

    /**
     * 当请求需要接受数据，但请求体为空时
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("请求体为空",ex);
        return Result.error(R.ERROR_QUERY, handlerMsg(R.ERROR_QUERY.msg));
    }


    public static String getLocal() {
        String lang = HttpUtil.getHttpInfo().getHeader(HeaderConstant.LANGUAGE);
        if (Objects.nonNull(lang)) {
            return lang;
        }
        return "zh";
    }

    public String handlerMsg(String msg) {
        try {
            return messageSource.getMessage(msg, null, Locale.forLanguageTag(getLocal()));
        } catch (Exception e) {
            e.printStackTrace();
            return msg;
        }
    }

    public String handlerMsg(List<String> msg) {
        return String.join(",", msg);
    }
}
