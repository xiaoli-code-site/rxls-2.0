package cn.rx.common.vo.sysPermissions;

import lombok.Data;
import java.util.List;

/**
 * 菜单权限
 */
@Data
public class SysMenuPermission {
    //描述
    private String title;
    /**
     * 权限
     */
    private List<SysPermissionsVO> permissions;
}
