package cn.rx.boot.controller.admin;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskAddDTO;
import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskPageDTO;
import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskUpdateDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.admin.sysSchedulerTask.SysSchedulerTaskPageVO;
import cn.rx.quartz.config.SchedulerTaskContext;
import cn.rx.quartz.service.SysSchedulerTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 定时任务(Admin)
 */
@RestController
@RequestMapping("/task")
public class SysSchedulerTaskController {
    /**
     * 服务对象
     */
    @Resource
    private SysSchedulerTaskService schedulerTaskService;

    @Resource
    private  SchedulerTaskContext schedulerTaskContext;

    /**
     * 新增定时任务
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/add")
    @RequiresPermissions("sys:task:add")
    @Oplog(leve = LogLeve.IMPORTANT, model = LogModel.SYS, description = "添加任务")
    public Result<?> addTask(@Valid @RequestBody SysSchedulerTaskAddDTO dto) {
        schedulerTaskService.addTask(dto);
        return Result.success();
    }


    /**
     * 分页查询定时任务信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @GetMapping("/auth/page")
    @RequiresPermissions("sys:task:select")
    public Result<SysSchedulerTaskPageVO> authPage(@Valid @ModelAttribute SysSchedulerTaskPageDTO dto) {
        return Result.success(schedulerTaskService.selectPage(dto));
    }


    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @PostMapping("/auth/del/id")
    @RequiresPermissions("sys:task:del")
    @Oplog(leve = LogLeve.IMPORTANT, model = LogModel.SYS, description = "任务删除")
    public Result<Integer> authDeleteInIdList(@Valid @RequestBody List<Integer> idList) {
        return Result.success(schedulerTaskService.deleteInIdList(idList));
    }


    /**
     * 变更定时任务信息
     *
     * @param dto 参数对象
     */
    @PostMapping("/auth/update")
    @RequiresPermissions("sys:task:update")
    @Oplog(leve = LogLeve.IMPORTANT, model = LogModel.SYS, description = "任务信息变更")
    public Result<?> authUpdateUser(@Valid @RequestBody SysSchedulerTaskUpdateDTO dto) {
        schedulerTaskService.updateTask(dto);
        return Result.success();
    }

    /**
     * 任务状态变更
     *
     * @param id 任务id
     */
    @GetMapping("/auth/update/state")
    @RequiresPermissions("sys:task:update")
    @Oplog(leve = LogLeve.CRITICAL, model = LogModel.SYS, description = "任务状态变更")
    public Result<?> stateUpdate(@RequestParam("id") Integer id) {
        schedulerTaskService.stateUpdate(id);
        return Result.success();
    }


    /**
     * 获取所有任务
     * @return 任务列表
     */
    @GetMapping("/auth/all")
    @RequiresPermissions("sys:task:select")
    public Result<Set<String>> allTask() {
        return Result.success(schedulerTaskContext.getTaskList());
    }



}

