package cn.rx.common.dto.admin.sysUser;


import cn.rx.common.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


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
     * 角色id列表
     */
    private List<Integer> roleIds;

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
     * 出生日期
     */
    @NotNull(message = "{bornNoEmpty}")
    @Past(message = "{illegalBorn}")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date born;

    /**
     * 性别 1男  0女
     */
    @NotNull(message = "{sexNoEmpty}")
    @Min(value = 0, message = "{illegalSex}")
    @Max(value = 1, message = "{illegalSex}")
    private Integer sex;


    public void setBorn(Date born) {
        this.born = born;
    }

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

    public void setRoleIds(List<Integer> roleIds) {
        if(Objects.isNull(roleIds)){
            this.roleIds = new ArrayList<>();
        }else{
            this.roleIds = roleIds;
        }
    }
}
