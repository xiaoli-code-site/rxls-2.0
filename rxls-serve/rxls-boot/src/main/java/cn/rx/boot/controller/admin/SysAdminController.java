package cn.rx.boot.controller.admin;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.admin.sysAdmin.SysAdminAddDTO;
import cn.rx.common.dto.admin.sysAdmin.SysAdminPageDTO;
import cn.rx.common.dto.admin.sysAdmin.SysAdminUpdateDTO;
import cn.rx.common.dto.admin.sysExcel.SysSysExcelAdminDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.admin.sysAdmin.SysAdminPageVO;
import cn.rx.system.service.SysAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 管理员(Admin)
 */
@RestController
@RequestMapping("/admin")
@Valid
public class SysAdminController {
    /**
     * 服务对象
     */
    @Resource
    private SysAdminService adminsService;



    /**
     * 新增管理员
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/add")
    @RequiresPermissions("sys:admin:add")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "添加管理员")
    public Result<?> add(@Valid @RequestBody SysAdminAddDTO dto) {
        adminsService.addAdmin(dto);
        return Result.success();
    }


    /**
     * 分页查询管理员信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @GetMapping("/auth/page")
    @RequiresPermissions("sys:admin:select")
    public Result<SysAdminPageVO> authPage(@Valid @ModelAttribute SysAdminPageDTO dto) {
        return Result.success(adminsService.selectPage(dto));
    }


    /**
     * 导出excel文件
     *
     * @param excelObj 参数对象
     * @param response 请求对象
     */
    @PostMapping("/auth/export/excel")
    @RequiresPermissions("sys:admin:export")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "管理员信息导出")
    public void authExportExcel(@Valid @RequestBody SysSysExcelAdminDTO excelObj, HttpServletResponse response) throws IOException {
        adminsService.export(excelObj, response);
    }


    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @PostMapping("/auth/del/id")
    @RequiresPermissions("sys:admin:del")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "管理员删除")
    public Result<Integer> authDeleteInIdList(@Valid @RequestBody List<Long> idList) {
        return Result.success(adminsService.deleteInIdList(idList));
    }


    /**
     * 变更管理员信息
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/update")
    @RequiresPermissions("sys:admin:update")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "管理员信息变更")
    public Result<?> authUpdateAdmin(@Valid @RequestBody SysAdminUpdateDTO dto) {
        adminsService.updateAdmin(dto);
        return Result.success();
    }

    /**
     * 变更管理员自己的信息
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/update/user")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "管理员信息变更")
    public Result<?> authUpdateAdminUser(@Valid @RequestBody SysAdminUpdateDTO dto) {
        adminsService.updateInfo(dto);
        return Result.success();
    }

}

