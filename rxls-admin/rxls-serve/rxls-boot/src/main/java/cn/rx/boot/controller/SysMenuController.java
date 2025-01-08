package cn.rx.boot.controller;


import cn.rx.common.result.Result;
import cn.rx.common.vo.sysPermissions.SysMenuPermission;
import cn.rx.system.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 菜单
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
    public Result<List<SysMenuPermission>> authPage() {
        return Result.success(sysMenuService.selectAllMenuPermission());
    }


}

