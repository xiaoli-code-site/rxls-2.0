package cn.rx.common.vo.admin.sysRole;

import cn.rx.common.vo.PageVO;
import cn.rx.common.vo.admin.sysPermissions.SysPermissionsVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分页查询角色
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRolePageVO extends PageVO<SysRoleVO> {
    /**
     * 权限列表
     */
    private List<SysPermissionsVO> permissions;
}
