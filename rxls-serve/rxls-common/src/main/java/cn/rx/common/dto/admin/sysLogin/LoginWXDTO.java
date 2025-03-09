package cn.rx.common.dto.admin.sysLogin;

import lombok.Data;

import javax.validation.constraints.NotBlank;



/**
 * 微信登录
 */
@Data
public class LoginWXDTO {
    /**
     * 标识码
     */
    @NotBlank(message = "{codeNoEmpty}")
    private String code;
}
