package cn.rx.boot.controller.admin;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.admin.sysConfig.SysConfigAddDTO;
import cn.rx.common.dto.admin.sysConfig.SysConfigPageDTO;
import cn.rx.common.dto.admin.sysConfig.SysConfigUpdateDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.admin.sysConfig.SysConfigPageVO;
import cn.rx.system.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 系统配置(Admin)
 */
@RestController
@RequestMapping("/config")
@Valid
public class SysConfigController {
    /**
     * 服务对象
     */
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 新增配置
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/add")
    @RequiresPermissions("sys:config:add")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "新增配置")
    public Result<?> addConfig(@Valid @RequestBody SysConfigAddDTO dto) {
        sysConfigService.addConfig(dto);
        return Result.success();
    }


    /**
     * 分页查询配置信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @GetMapping("/auth/page")
    @RequiresPermissions("sys:config:select")
    public Result<SysConfigPageVO> authPage(@Valid @ModelAttribute SysConfigPageDTO dto) {
        return Result.success(sysConfigService.selectPage(dto));
    }



    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @PostMapping("/auth/del/id")
    @RequiresPermissions("sys:config:del")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "配置删除")
    public Result<Integer> authDeleteInIdList(@Valid @RequestBody List<Integer> idList) {
        return Result.success(sysConfigService.deleteInIdList(idList));
    }


    /**
     * 变更配置信息
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/update")
    @RequiresPermissions("sys:config:update")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "配置更新")
    public Result<?> authUpdateConfig(@Valid @RequestBody SysConfigUpdateDTO dto) {
        sysConfigService.updateConfig(dto);
        return Result.success();
    }


    /**
     * 配置状态变更
     *
     * @param id 配置id
     */
    @GetMapping("/auth/update/state")
    @RequiresPermissions("sys:config:update")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "配置状态变更")
    public Result<?> stateUpdateConfig(@RequestParam("id") Integer id) {
        sysConfigService.stateUpdateConfig(id);
        return Result.success();
    }
}
