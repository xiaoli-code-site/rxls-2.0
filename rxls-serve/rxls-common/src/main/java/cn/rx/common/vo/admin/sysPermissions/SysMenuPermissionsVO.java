package cn.rx.common.vo.admin.sysPermissions;

import lombok.Data;
import java.util.List;

/**
 * 菜单权限
 */
@Data
public class SysMenuPermissionsVO {
    //描述
    private String title;
    /**
     * 权限
     */
    private List<SysPermissionsVO> permissions;
}
