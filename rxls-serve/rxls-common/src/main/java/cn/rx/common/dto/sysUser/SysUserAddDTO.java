package cn.rx.common.dto.sysUser;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 用户新增
 */
@Data
public class SysUserAddDTO {
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
     * 1男  0女
     */
    @NotNull(message = "{sexNoEmpty}")
    @Min(value = 0,message = "{illegalSex}")
    @Max(value = 1,message = "{illegalSex}")
    private Integer sex;

    /**
     * 角色id
     */
    @NotNull(message = "{roleNoEmpty}")
    @Min(value = 1,message = "{illegalRole}")
    private Integer role;

}
