package cn.rx.common.dto.sysUser;

import cn.rx.common.dto.PageDTO;
import cn.rx.common.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 用户分页查询
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class SysUserPageDTO extends PageDTO {
    /**
     * 账号
     */
    private String username;

    /**
     * 角色id
     */
    private Integer role;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 1男  0女
     */
    private Integer sex;

    public void setPhone(String phone) {
        this.phone = StrUtil.validateValue(phone);
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

    public void setRole(Integer role) {
        this.role = role;
    }

}
