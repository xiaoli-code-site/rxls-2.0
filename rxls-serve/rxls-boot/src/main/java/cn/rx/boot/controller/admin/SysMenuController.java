package cn.rx.boot.controller.admin;


import cn.rx.common.result.Result;
import cn.rx.common.vo.admin.sysMenu.SysAllMenuPermissionVO;
import cn.rx.common.vo.admin.sysMenu.SysMenuVO;
import cn.rx.common.vo.admin.sysPermissions.SysMenuPermissionsVO;
import cn.rx.system.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * 菜单(Admin)
 */
@RestController
@RequestMapping("menu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;


    /**
     * 查询所有菜单权限信息
     *
     * @return 数据列表
     */
    @GetMapping("/all")
    public Result<SysAllMenuPermissionVO> authPage() {
        return Result.success(sysMenuService.selectAllMenuPermission());
    }

    /**
     * 根据角色查询菜单权限信息
     *
     * @return 数据列表
     */
    @GetMapping("/role")
    public Result<Set<SysMenuVO>> authRole(@RequestParam("roleId") Integer roleId) {
        return Result.success(sysMenuService.selectRoleMenuPermission(roleId));
    }

}

