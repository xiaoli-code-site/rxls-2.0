package cn.rx.common.dto.sysAdmin;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增管理员
 */
@Data
public class SysAdminAddDTO {
    /**
     * 用户名、账号
     */
    @NotBlank(message = "{usernameNoEmpty}")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "{passwordNoEmpty}")
    private String password;
    /**
     * 手机号
     */
    @NotBlank(message = "{phoneNumberNoEmpty}")
    private String phone;
    /**
     * 用户头像
     */
    @NotBlank(message = "{avatarNoEmpty}")
    private String avatar;
    /**
     * 用户名称
     */
    @NotBlank(message = "{nicknameNoEmpty}")
    private String nickname;
    /**
     * 性别
     */
    @NotNull(message = "{sexNoEmpty}")
    @Min(value = 0,message = "{illegalSex}")
    @Max(value = 1,message = "{illegalSex}")
    private Integer sex;

    /**
     * 角色
     */
    @NotBlank(message = "{roleNoEmpty}")
    private String role;
}
