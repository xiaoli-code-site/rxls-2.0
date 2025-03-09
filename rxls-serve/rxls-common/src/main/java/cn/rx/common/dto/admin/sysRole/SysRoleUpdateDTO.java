package cn.rx.common.dto.admin.sysRole;

import cn.rx.common.util.StrUtil;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色更新
 */
@Data
public class SysRoleUpdateDTO {
    /**
     * 状态
     */
    @NotNull(message = "{idNotEmpty}")
    @Min(value = 1, message = "{illegalId}")
    private Integer roleId;
    /**
     * 角色名
     */
    private String roleName;

    /**
     * 权限ID列表
     */
    private List<Integer> permissions;

    /**
     * 菜单ID列表
     */
    private List<Integer> menus;

    /**
     * 权限值
     */
    @Min(value = 1, message = "{illegalRoleValue}")
    @Max(value = 99999, message = "{illegalRoleValue}")
    private Integer roleValue;

    /**
     * 状态
     */
    @NotNull(message = "{stateNoEmpty}")
    @Min(value = 0, message = "{illegalState}")
    @Max(value = 1, message = "{illegalState}")
    private Integer state;

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = StrUtil.validateValue(roleName);
    }


    public void setState(Integer state) {
        this.state = state;
    }

    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
    }

    public void setMenus(List<Integer> menus) {
        this.menus = menus;
    }
}
