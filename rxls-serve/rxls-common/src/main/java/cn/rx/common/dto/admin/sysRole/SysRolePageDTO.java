package cn.rx.common.dto.admin.sysRole;

import cn.rx.common.dto.admin.PageDTO;
import cn.rx.common.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 角色分页查询
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class SysRolePageDTO extends PageDTO {

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 状态
     */
    private Integer state;


    public void setRoleName(String roleName) {
        this.roleName = StrUtil.validateValue(roleName);
    }


    public void setState(Integer state) {
        this.state = state;
    }
}
