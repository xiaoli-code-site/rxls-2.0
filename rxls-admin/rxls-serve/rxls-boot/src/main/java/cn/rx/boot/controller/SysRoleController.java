package cn.rx.boot.controller;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.sysRole.SysRoleAddDTO;
import cn.rx.common.dto.sysRole.SysRolePageDTO;
import cn.rx.common.dto.sysRole.SysRoleUpdateDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.sysRole.SysRolePageVO;
import cn.rx.common.vo.sysRole.SysRoleVO;
import cn.rx.system.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 获取所有的角色
     *
     * @return 角色列表
     */
    @GetMapping("/auth/all")
    @RequiresPermissions("sys:role:select")
    public Result<List<SysRoleVO>> authSelectAll() {
        return Result.success(sysRoleService.selectAll());
    }


    /**
     * 新增角色
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/add")
    @RequiresPermissions("sys:role:add")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "添加角色")
    public Result<?> addRole(@Valid @RequestBody SysRoleAddDTO dto) {
        sysRoleService.addRole(dto);
        return Result.success();
    }


    /**
     * 分页查询角色信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @GetMapping("/auth/page")
    @RequiresPermissions("sys:role:select")
    public Result<SysRolePageVO> authPage(@Valid @ModelAttribute SysRolePageDTO dto) {
        return Result.success(sysRoleService.selectPage(dto));
    }



    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @PostMapping("/auth/del/id")
    @RequiresPermissions("sys:role:del")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "角色删除")
    public Result<Integer> authDeleteInIdList(@Valid @RequestBody List<Integer> idList) {
        return Result.success(sysRoleService.deleteInIdList(idList));
    }


    /**
     * 变更角色信息
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/update")
    @RequiresPermissions("sys:role:update")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "角色更新")
    public Result<?> authUpdateRole(@Valid @RequestBody SysRoleUpdateDTO dto) {
        sysRoleService.updateRole(dto);
        return Result.success();
    }
}

