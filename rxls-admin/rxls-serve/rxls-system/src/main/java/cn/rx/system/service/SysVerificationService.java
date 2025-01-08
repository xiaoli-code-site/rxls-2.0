package cn.rx.system.service;


import cn.rx.system.entity.VerificationCode;

/**
 * 验证码接口
 * @author 不想说话而已
 */
public interface SysVerificationService {

    /**
     * 获取验证码
     * @param type 验证码类型
     * @return 验证码对象
     */
    VerificationCode getCode(String type);


    /**
     * 验证码校验检查
     * @param code 验证码对象
     * @return 是否正确
     */
    Boolean verification(VerificationCode code);


}
