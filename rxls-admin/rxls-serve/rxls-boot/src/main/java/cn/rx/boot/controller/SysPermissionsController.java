package cn.rx.boot.controller;


import cn.rx.common.result.Result;
import cn.rx.common.vo.sysPermissions.SysPermissionsVO;
import cn.rx.system.service.SysPermissionsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * 权限
 */
@RestController
@RequestMapping("permissions")
public class SysPermissionsController {
    /**
     * 服务对象
     */
    @Resource
    private SysPermissionsService sysPermissionsService;


    /**
     * 获取所有的权限
     *
     * @return 权限列表
     */
    @GetMapping("/auth/all")
    @RequiresPermissions("sys:permissions:select")
    public Result<List<SysPermissionsVO>> authSelectAll() {
        return Result.success(sysPermissionsService.selectAll());
    }


    /**
     * 获取角色所有的权限
     * @param roleId 角色id
     * @return 权限列表
     */
    @GetMapping("/auth/role/all")
    @RequiresPermissions("sys:permissions:select")
    public Result<Set<SysPermissionsVO>> selectAllByRole(@RequestParam("roleId") Integer roleId) {
        return Result.success(sysPermissionsService.selectAllByRoleId(roleId));
    }

}

