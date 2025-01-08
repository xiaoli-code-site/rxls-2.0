package cn.rx.system.service.impl;


import cn.rx.common.util.CaptchaUtil;
import cn.rx.system.entity.VerificationCode;
import cn.rx.system.service.SysVerificationService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 验证码
 * @author 不想说话而已
 */
@Service("VerificationService")
public class SysVerificationImpl implements SysVerificationService {


    /**
     * 获取验证码
     *
     * @return 验证码对象
     */
    @Override
    public VerificationCode getCode(String type) {
        // 创建验证码对象
        Captcha captcha = new ArithmeticCaptcha();
        String key = UUID.randomUUID().toString();
        //缓存本地
        CaptchaUtil.setCache(key,captcha.text(),type);
        return new VerificationCode(type,key, captcha.toBase64(),null);
    }

    /**
     * 验证码校验检查
     *
     * @param code 验证码对象
     * @return 是否正确
     */
    @Override
    public Boolean verification(VerificationCode code) {
        return  CaptchaUtil.verify(code.getCodeKey(),code.getText(),code.getTypes());
    }
}
