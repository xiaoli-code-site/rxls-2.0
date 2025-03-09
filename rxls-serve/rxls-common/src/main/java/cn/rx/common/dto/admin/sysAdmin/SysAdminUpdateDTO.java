package cn.rx.common.dto.admin.sysAdmin;

import cn.rx.common.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


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
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date born;

    /**
     * 角色id列表
     */
    private List<Integer> roleIds;
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

    public void setBorn(Date born) {
        this.born = born;
    }

    public void setAvatar(String avatar) {
        this.avatar =  StrUtil.validateValue(avatar);
    }
    public void setRoleIds(List<Integer> roleIds) {
        if(Objects.isNull(roleIds)){
            this.roleIds = new ArrayList<>();
        }else{
            this.roleIds = roleIds;
        }
    }

    public void setPassword(String password) {
        this.password = StrUtil.validateValue(password);
    }
}
