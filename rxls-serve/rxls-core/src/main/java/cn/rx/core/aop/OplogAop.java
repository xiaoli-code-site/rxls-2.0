package cn.rx.core.aop;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.constant.HeaderConstant;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.util.HttpUtil;
import cn.rx.common.util.IpUtil;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysOplog;
import cn.rx.db.mapper.SysOplogMapper;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * 操作日志记录
 */
@Aspect
@Component
public class OplogAop {


    @Resource
    SysOplogMapper sysOplogMapper;

    @Resource
    Ip2regionSearcher ip2regionSearcher;

    @Resource
    private MessageSource messageSource;


    @Pointcut("@annotation(cn.rx.common.annotations.Oplog)")
    public void aspect() {
    }


    @Around("aspect()")
    public Object handler(ProceedingJoinPoint pjp) throws Throwable {

//        if(1==1){
//            throw new BusinessException(R.ERROR.code,"演示模式，不容许操作");
//        }



        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Oplog log = method.getAnnotation(Oplog.class);
        Object proceed = null;
        try {
            proceed = pjp.proceed();
        } catch (Throwable e) {
            Map<String, Object> info = new HashMap<>();
            if (e instanceof BusinessException) {
                info.put("code", ((BusinessException) e).getCode());
                info.put("message", handlerMsg(e.getMessage()));
                info.put("package", method.getDeclaringClass().getName());
                info.put("method", method.getName());
            }
            stopWatch.stop();
            setLog(log.leve(), CommonStateEnum.ERROR, log.model(), log.description(), stopWatch.getTotalTimeMillis(), pjp.getArgs(), info);
            throw e;
        }
        stopWatch.stop();
        setLog(log.leve(), CommonStateEnum.OK, log.model(), log.description(), stopWatch.getTotalTimeMillis(), pjp.getArgs(), proceed);
        return proceed;
    }


    /**
     * 通用日志记录
     *
     * @param leve        级别
     * @param state       状态
     * @param model       模块
     * @param description 描述
     * @param times       耗时
     * @param query       操作参数
     * @param result      结果
     */
    public void setLog(LogLeve leve, CommonStateEnum state, LogModel model, String description, long times, Object query, Object result) {
        String ipAddress = IpUtil.getIpAddress(HttpUtil.getHttpInfo());
        String address = ip2regionSearcher.getAddress(ipAddress);
        SysOplog sysOplog = new SysOplog();
        sysOplog.setState(state.code);

        if (Objects.nonNull(query)) {
            sysOplog.setQuery(JSON.toJSONString(query));
        }

        sysOplog.setResult(JSON.toJSONString(result));

        Claims userLoginInfo = HttpUtil.getUserLoginInfo();
        if (Objects.nonNull(userLoginInfo) && Objects.isNull(sysOplog.getUserId())) {
            sysOplog.setUserId(Long.valueOf(userLoginInfo.getSubject()));
        }

        sysOplog.setTimes(times);
        sysOplog.setIp(ipAddress);
        sysOplog.setAddress(address);
        sysOplog.setDescription(description);
        sysOplog.setModel(model.value);
        sysOplog.setCategory(leve.value);

        sysOplogMapper.insert(sysOplog);
    }

    /**
     * 国际化
     *
     * @param msg 内容
     * @return 国际化内容
     */
    public String handlerMsg(String msg) {
        try {
            return messageSource.getMessage(msg, null, Locale.forLanguageTag(getLocal()));
        } catch (Exception e) {
            e.printStackTrace();
            return msg;
        }
    }

    public static String getLocal() {
        String lang = HttpUtil.getHttpInfo().getHeader(HeaderConstant.LANGUAGE);
        if (Objects.nonNull(lang)) {
            return lang;
        }
        return "zh";
    }
}
