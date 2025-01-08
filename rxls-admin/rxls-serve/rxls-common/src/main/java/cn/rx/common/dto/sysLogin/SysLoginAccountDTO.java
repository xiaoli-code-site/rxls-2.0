package cn.rx.common.dto.sysLogin;

import lombok.Data;

import javax.validation.constraints.NotBlank;



/**
 * 登录
 */
@Data
public class SysLoginAccountDTO {
    /**
     * 账号
     */
    @NotBlank(message = "{usernameNoEmpty}")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "{passwordNoEmpty}")
    private String password;

    /**
     * 验证码Key
     */
    @NotBlank(message = "{captchaNotEmpty}")
    private String verifyCodeKey;

    /**
     * 验证码
     */
    @NotBlank(message = "{captchaNotEmpty}")
    private String verifyCode;
}
