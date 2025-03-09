package cn.rx.boot.controller.admin;


import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.admin.sysPermissions.SysPermissionsAddDTO;
import cn.rx.common.dto.admin.sysPermissions.SysPermissionsPageDTO;
import cn.rx.common.dto.admin.sysPermissions.SysPermissionsUpdateDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.admin.sysPermissions.SysPermissionsPageVO;
import cn.rx.common.vo.admin.sysPermissions.SysPermissionsVO;
import cn.rx.system.service.SysPermissionsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


/**
 * 权限表(Permissions)
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


    /**
     * 新增
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/add")
    @RequiresPermissions("sys:permissions:add")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "添加权限")
    public Result<?> add(@Valid @RequestBody SysPermissionsAddDTO dto) {
        sysPermissionsService.add(dto);
        return Result.success();
    }


    /**
     * 分页查询信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @GetMapping("/auth/page")
    @RequiresPermissions("sys:permissions:select")
    public Result<SysPermissionsPageVO> selectPage(@Valid @ModelAttribute SysPermissionsPageDTO dto) {
        return Result.success(sysPermissionsService.selectPage(dto));
    }



    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @PostMapping("/auth/del/id")
    @RequiresPermissions("sys:permissions:del")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "权限删除")
    public Result<Integer> deleteInIdList(@Valid @RequestBody List<Integer> idList) {
        return Result.success(sysPermissionsService.deleteInIdList(idList));
    }


    /**
     * 变更信息
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/update")
    @RequiresPermissions("sys:permissions:update")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "权限更新")
    public Result<?> alter(@Valid @RequestBody SysPermissionsUpdateDTO dto) {
        sysPermissionsService.alter(dto);
        return Result.success();
    }

}

