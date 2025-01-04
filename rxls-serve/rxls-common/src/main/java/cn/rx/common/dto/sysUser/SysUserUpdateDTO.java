package cn.rx.common.dto.sysUser;


import cn.rx.common.util.StrUtil;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 用户更新
 */
@Getter
@Data
public class SysUserUpdateDTO {
    /**
     * 主键id
     */
    @NotNull(message = "{idNotEmpty}")
    @Min(value = 1, message = "{illegalId}")
    private Long userId;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String roleName;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 状态
     */
    @NotNull(message = "{stateNoEmpty}")
    @Min(value = 0, message = "{illegalState}")
    @Max(value = 1, message = "{illegalState}")
    private Integer state;


    /**
     * 1男  0女
     */
    @NotNull(message = "{sexNoEmpty}")
    @Min(value = 0, message = "{illegalSex}")
    @Max(value = 1, message = "{illegalSex}")
    private Integer sex;



    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public void setPhone(String phone) {
        this.phone = StrUtil.validateValue(phone);
    }

    public void setAvatar(String avatar) {
        this.avatar = StrUtil.validateValue(avatar);
    }

    public void setNickname(String nickname) {
        this.nickname = StrUtil.validateValue(nickname);
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setUsername(String username) {
        this.username = StrUtil.validateValue(username);
    }

    public void setPassword(String password) {
        this.password = StrUtil.validateValue(password);
    }

    public void setRoleName(String roleName) {
        this.roleName = StrUtil.validateValue(roleName);
    }
}
