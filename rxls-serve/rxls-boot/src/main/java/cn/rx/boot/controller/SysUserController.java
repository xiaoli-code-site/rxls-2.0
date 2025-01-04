package cn.rx.boot.controller;


import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.sysExcel.SysExcelUserDTO;
import cn.rx.common.dto.sysUser.SysUserAddDTO;
import cn.rx.common.dto.sysUser.SysUserPageDTO;
import cn.rx.common.dto.sysUser.SysUserUpdateDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.sysUser.SysUserPageVO;
import cn.rx.system.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 用户
 */
@RestController
@RequestMapping("/user")
@Validated
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;


    /**
     * 新增用户
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/add")
    @RequiresPermissions("sys:user:add")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "添加用户")
    public Result<?> addUser(@Valid @RequestBody SysUserAddDTO dto) {
        sysUserService.addUser(dto);
        return Result.success();
    }


    /**
     * 分页查询用户信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @GetMapping("/auth/page")
    @RequiresPermissions("sys:user:select")
    public Result<SysUserPageVO> authPage(@Valid @ModelAttribute SysUserPageDTO dto) {
        return Result.success(sysUserService.selectPage(dto));
    }


    /**
     * 导出excel文件
     *
     * @param excelObj 参数对象
     * @param response 请求对象
     */
    @PostMapping("/auth/export/excel")
    @RequiresPermissions("sys:user:excel")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "用户信息导出")
    public void authExportExcel(@Valid @RequestBody SysExcelUserDTO excelObj, HttpServletResponse response) throws IOException {
        sysUserService.export(excelObj, response);
    }


    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @PostMapping("/auth/del/id")
    @RequiresPermissions("sys:user:del")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "用户删除")
    public Result<Integer> authDeleteInIdList(@Valid @RequestBody List<Long> idList) {
        return Result.success(sysUserService.deleteInIdList(idList));
    }


    /**
     * 变更用户信息
     *
     * @param user 参数对象
     */
    @PostMapping("/auth/update")
    @RequiresPermissions("sys:user:update")
    @Oplog(leve = LogLeve.IMPORTANT,model = LogModel.SYS,description = "用户信息变更")
    public Result<?> authUpdateUser(@Valid @RequestBody SysUserUpdateDTO user) {
        sysUserService.updateUser(user);
        return Result.success();
    }


}

