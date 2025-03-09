package cn.rx.common.dto.admin.sysUser;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

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
     * 出生日期
     */
    @NotNull(message = "{bornNoEmpty}")
    @Past(message = "{illegalBorn}")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date born;
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
     * 角色id列表
     */
    @NotNull(message = "{roleNoEmpty}")
    @Size(min = 1, message = "{roleNoEmpty}")
    private List<Integer> roleIds;

}
