package cn.rx.common.dto.admin.sysAdmin;

import cn.rx.common.dto.admin.PageDTO;
import cn.rx.common.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 分页查询管理员
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class SysAdminPageDTO extends PageDTO {

    /**
     * 用户名、账号
     */
    private String username;

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
     * 性别
     */
    private Integer sex;

    /**
     * 角色id
     */
    private Integer roleId;

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setUsername(String username) {
        this.username = StrUtil.validateValue(username);
    }

    public void setPhone(String phone) {
        this.phone =  StrUtil.validateValue(phone);
    }

    public void setNickname(String nickname) {
        this.nickname =  StrUtil.validateValue(nickname);
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


}
