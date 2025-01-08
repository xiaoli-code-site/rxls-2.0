package cn.rx.boot.controller;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.sysOplog.SysOplogPageDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.sysOplog.SysOplogPageVO;
import cn.rx.system.service.SysOplogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 日志
 */
@RestController
@RequestMapping("oplog")
@Slf4j
public class SysOplogController {

    @Resource
    private SysOplogService sysOplogService;
    /**
     * 分页查询日志信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @GetMapping("/auth/page")
    @RequiresPermissions("sys:oplog:select")
    public Result<SysOplogPageVO> authPage(@Valid @ModelAttribute SysOplogPageDTO dto) {
        return Result.success(sysOplogService.selectPage(dto));
    }



    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @PostMapping("/auth/del/id")
    @RequiresPermissions("sys:oplog:del")
    @Oplog(leve = LogLeve.CRITICAL,model = LogModel.SYS,description = "操作日志删除")
    public Result<Integer> authDeleteInIdList(@Valid @RequestBody List<Long> idList) {
        return Result.success(sysOplogService.deleteInIdList(idList));
    }
}
