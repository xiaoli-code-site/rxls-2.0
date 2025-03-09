package cn.rx.auth.shiro.filter;

import cn.rx.common.constant.HeaderConstant;
import cn.rx.common.constant.JwtConstant;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.util.HttpUtil;
import cn.rx.common.util.IpUtil;
import cn.rx.common.util.JwtUtil;
import cn.rx.db.entity.SysOplog;
import cn.rx.db.mapper.SysOplogMapper;
import cn.rx.auth.shiro.JwtToken;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/*
 * 自定义过滤器
 */
@Slf4j
@Component
public class JwtFilter extends AccessControlFilter {

    private final SysOplogMapper oplogMapper;

    private final Ip2regionSearcher ip2regionSearcher;

    private final MessageSource messageSource;

    public JwtFilter(SysOplogMapper oplogMapper,  Ip2regionSearcher ip2regionSearcher,MessageSource messageSource){
        this.ip2regionSearcher = ip2regionSearcher;
        this.oplogMapper = oplogMapper;
        this.messageSource = messageSource;
    }



    /*
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //这里让它始终返回false来使用onAccessDenied()方法
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader(JwtConstant.TOKEN);
        JwtToken jwtToken = new JwtToken(jwt);
        try {
            // 委托 realm 进行登录认证
            getSubject(servletRequest, servletResponse).login(jwtToken);
        } catch (Exception e) {
            onLoginFail(servletResponse,jwt,e);
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void onLoginFail(ServletResponse response,String token,Exception e) throws IOException {
        SysOplog sysOplog = new SysOplog();
        Claims claims = JwtUtil.parseToken(token);
        Optional.ofNullable(claims).ifPresent(s->{
            sysOplog.setUserId(Long.valueOf(claims.getSubject()));
        });
        String ipAddress = IpUtil.getIpAddress(HttpUtil.getHttpInfo());
        String address = ip2regionSearcher.getAddress(ipAddress);
        sysOplog.setQuery(token);
        sysOplog.setCategory(LogLeve.CRITICAL.value);
        sysOplog.setDescription("认证失败");
        sysOplog.setAddress(address);
        sysOplog.setIp(ipAddress);
        sysOplog.setModel(LogModel.SECURE.value);
        sysOplog.setResult(e.getLocalizedMessage());
        sysOplog.setState(CommonStateEnum.ERROR.code);
        oplogMapper.insert(sysOplog);


        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.setCharacterEncoding("utf-8");
        //没有登录
        if(Objects.isNull(token) || "".equals(token)){
            String msg = handlerMsg(R.ERROR_LOGIN_NOT.msg);
            Result<?> error = Result.error(R.ERROR_LOGIN_NOT.code,msg);
            httpResponse.getWriter().write(JSON.toJSONString(error));
            return;
        }
        String msg = handlerMsg(R.ERROR_ATTESTATION_NO.msg);
        Result<?> error = Result.error(R.ERROR_ATTESTATION_NO.code,msg);
        httpResponse.getWriter().write(JSON.toJSONString(error));
    }

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
