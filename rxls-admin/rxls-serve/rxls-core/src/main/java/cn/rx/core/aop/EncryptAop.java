package cn.rx.core.aop;


import cn.rx.common.result.Result;
import cn.rx.common.util.HttpUtil;
import cn.rx.core.encrypt.rsa.RSAUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 响应请求体加密注解
 */
@Aspect
@Component
public class EncryptAop {


    @Pointcut("@annotation(cn.rx.common.annotations.Encrypt)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object bodyEncrypt(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        if (proceed instanceof Result) {
            Result<Object> result = (Result) proceed;
            Object token = HttpUtil.getHttpInfo().getAttribute("apiKey");
//            Optional.ofNullable(result.getData()).ifPresent(s -> {
//                try {
//                    encryptText = RSAUtil.encrypt(AESUtil(key, JSON.toJSONString(result.getData())));
//                } catch (Exception encrypt) {
//                    throw new BusinessException(R.ERROR_ENCRYPT);
//                }
//                result.setData(encryptText);
//
//            });
            return result;
        }

        return proceed;
    }

}
