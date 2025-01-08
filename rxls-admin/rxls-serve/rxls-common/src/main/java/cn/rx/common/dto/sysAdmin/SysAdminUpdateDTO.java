package cn.rx.common.dto.sysAdmin;

import cn.rx.common.util.StrUtil;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 更新管理员
 */
@Getter
public class SysAdminUpdateDTO {
    /**
     * 主键id
     */
    @NotNull(message = "{idNotEmpty}")
    @Min(value = 1,message = "{illegalId}")
    private Long userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;



    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 角色
     */
    private String roleName;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 状态
     */
    @Min(value = 0, message = "{illegalState}")
    @Max(value = 1, message = "{illegalState}")
    private Integer state;
    /**
     * 性别
     */
    @Min(value = 0, message = "{illegalSex}")
    @Max(value = 1, message = "{illegalSex}")
    private Integer sex;

    public void setPhone(String phone) {
        this.phone = StrUtil.validateValue(phone);
    }

    public void setNickname(String nickname) {
        this.nickname = StrUtil.validateValue(nickname);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setRoleName(String roleName) {
        this.roleName = StrUtil.validateValue(roleName);
    }

    public void setAvatar(String avatar) {
        this.avatar =  StrUtil.validateValue(avatar);
    }

    public void setPassword(String password) {
        this.password = StrUtil.validateValue(password);
    }
}
