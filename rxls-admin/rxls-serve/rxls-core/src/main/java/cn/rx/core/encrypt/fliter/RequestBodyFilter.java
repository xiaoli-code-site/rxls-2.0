package cn.rx.core.encrypt.fliter;


import cn.rx.common.constant.HeaderConstant;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.core.encrypt.CiphertextObj;
import cn.rx.core.encrypt.aes.AESUtil;
import cn.rx.core.encrypt.rsa.RSAUtil;
import cn.rx.core.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * 过滤器
 * RequestBody内容加解密过滤器
 * @author 不想说话而已
 */

@Component
@Slf4j
public class RequestBodyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("RequestBody内容加解密过滤器执行");
        // 预检请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 请求是否加密
        String isEncrypt = request.getHeader(HeaderConstant.ENCRYPT_KEY);
        //如果加密头不存在，直接放行
        if (Objects.isNull(isEncrypt) || "".equals(isEncrypt) || Integer.valueOf(isEncrypt).equals(CommonStateEnum.ERROR.code)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        if ("POST".equals(request.getMethod())) {
            //如果请求已经加密
            ServletRequestDataWrapper servletRequestDataWrapper = new ServletRequestDataWrapper(request);
            String bodyContent = servletRequestDataWrapper.getBodyString();
            Optional.ofNullable(bodyContent).orElseThrow(() -> new BusinessException(R.ERROR_DECRYPT));
            CiphertextObj ciphertextObj = JSON.parseObject(bodyContent, CiphertextObj.class);
            // 使用加密秘钥获取明文内容
            String decrypt = null;
            try {
                //解密apiKey 获得加密秘钥
                String apiKey = RSAUtil.decrypt(ciphertextObj.getApiKey());
                Optional.ofNullable(apiKey).orElseThrow(() -> new BusinessException(R.ERROR_DECRYPT));
                decrypt = AESUtil.aesDecrypt(ciphertextObj.getEncryptText(), apiKey);
                log.debug("请求接口解密内容{}", decrypt);
                servletRequestDataWrapper.setAttribute("apiKey", apiKey);
                servletRequestDataWrapper.setBody(decrypt.getBytes());
                filterChain.doFilter(servletRequestDataWrapper, servletResponse);
            } catch (Exception e) {
                log.error("请求接口解密失败", e);
                throw new BusinessException(R.ERROR_DECRYPT);
            }
            return;
        }

        // 将请求传递给下一个过滤器
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
