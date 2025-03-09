package cn.rx.common.vo.admin.sysMenu;

import cn.rx.common.vo.admin.sysPermissions.SysMenuPermissionsVO;
import lombok.Data;

import java.util.List;

/**
 * 所有的菜单权限信息
 */
@Data
public class SysAllMenuPermissionVO {

    /**
     * 权限信息
     */
    private List<SysMenuPermissionsVO> permissions;

    /**
     * 菜单信息
     */
    private List<SysMenuVO> menus;
}
