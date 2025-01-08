package cn.rx.quartz.config;

import cn.rx.common.enums.CommonStateEnum;
import cn.rx.db.entity.SysSchedulerTask;
import cn.rx.db.mapper.SysSchedulerTaskMapper;
import cn.rx.quartz.entity.SchedulerTask;
import cn.rx.quartz.util.TriggerUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务调度器
 */
@Component
@Slf4j
public class SchedulerTaskContext {

    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    @Resource
    SysSchedulerTaskMapper sysSchedulerTaskMapper;

    /**
     * 所有的任务列表
     */
    @Resource
    private Map<String, Job> jobMap;


    /**
     * 任务执行列表
     */
    private static final Map<String, JobDetail> tasks = new ConcurrentHashMap<>();


    /**
     * 初始化任务列表
     */
    @PostConstruct
    private void init() {
        LambdaQueryWrapper<SysSchedulerTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSchedulerTask::getState, CommonStateEnum.OK.code);
        List<SysSchedulerTask> sysSchedulerTasks = sysSchedulerTaskMapper.selectList(wrapper);
        sysSchedulerTasks.forEach(s -> {
            Job job = jobMap.get(s.getJobCode());
            Optional.ofNullable(job).ifPresent(j -> {
                try {
                    if (CommonStateEnum.OK.code.equals(s.getState())) {
                        SchedulerTask schedulerTask = new SchedulerTask();
                        BeanUtils.copyProperties(s, schedulerTask);
                        start(schedulerTask, j, TriggerUtil.get(s.getModel(), s.getExpression(), s.getUnit()));

                    }
                } catch (SchedulerException e) {
                    e.printStackTrace();

                }
            });
        });
    }


    /**
     * 添加任务
     *
     * @param schedulerTask 定时任务信息
     */
    public void addTask(SchedulerTask schedulerTask) {
        Job job = jobMap.get(schedulerTask.getJobCode());
        delTask(schedulerTask.getJobCode(), schedulerTask.getJobName(), schedulerTask.getGroupName());
        Optional.ofNullable(job).ifPresent(s -> {
            try {
                start(schedulerTask, job, schedulerTask.getScheduleBuilder());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 恢复执行任务
     *
     * @param schedulerTask 任务对象
     */
    public void resumeTask(SchedulerTask schedulerTask) {
        JobDetail jobDetail = tasks.get(schedulerTask.getJobCode());
        if (Objects.isNull(jobDetail)) {
            addTask(schedulerTask);
            return;
        }
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.resumeJob(new JobKey(schedulerTask.getJobName(), schedulerTask.getGroupName()));
        } catch (SchedulerException e) {
            setErrorLog("恢复定时任务失败！", schedulerTask.getJobName(), schedulerTask.getGroupName(), e);
        }

    }

    /**
     * 暂停任务
     *
     * @param jobName 任务名称
     * @param group   分组
     */
    public void pauseTask(String jobCode, String jobName, String group) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail = tasks.get(jobCode);
            if (Objects.isNull(jobDetail)) {
                return;
            }
            // 暂停特定的 Job
            scheduler.pauseJob(new JobKey(jobName, group));
        } catch (Exception e) {
            setErrorLog("暂停定时任务失败！", jobName, group, e);

        }
    }

    /**
     * 删除任务
     */
    public void delTask(String jobCode, String jobName, String group) {
        JobDetail jobDetail = tasks.get(jobCode);
        if (Objects.isNull(jobDetail)) {
            return;
        }
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.deleteJob(jobDetail.getKey());
            tasks.remove(jobCode);
        } catch (SchedulerException e) {
            setErrorLog("定时任务删除失败！", jobName, group, e);
        }
    }

    /**
     * 重启任务
     */
    public void resetTask(SchedulerTask schedulerTask) {
        Job job = jobMap.get(schedulerTask.getJobCode());
        if (Objects.isNull(job)) {
            return;
        }
        JobDetail jobDetail = tasks.get(schedulerTask.getJobCode());
        Optional.ofNullable(jobDetail).ifPresent(s -> {
            delTask(schedulerTask.getJobCode(), schedulerTask.getJobName(), schedulerTask.getGroupName());
        });
        try {
            start(schedulerTask, job, schedulerTask.getScheduleBuilder());
        } catch (SchedulerException e) {
            setErrorLog("重启定时任务失败！", schedulerTask.getJobName(), schedulerTask.getGroupName(), e);
        }
    }


    /**
     * 任务执行
     *
     * @param task            任务对象
     * @param job             任务实现类
     * @param scheduleBuilder 触发器
     */
    private void start(SchedulerTask task, Job job, ScheduleBuilder scheduleBuilder) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(task.getJobName(), task.getGroupName()).build();
        TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger().withIdentity(task.getJobCode(), task.getGroupName());
        triggerTriggerBuilder.startAt(task.getStartTime());
        triggerTriggerBuilder.endAt(task.getEndTime());
        Trigger trigger = triggerTriggerBuilder.withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.getListenerManager().addJobListener(new GlobalTriggerExecutionListener(scheduler,sysSchedulerTaskMapper));
        scheduler.start();
        tasks.put(task.getJobCode(), jobDetail);
    }


    /**
     * 失败日志记录
     *
     * @param msg     提示信息
     * @param jobName 任务名称
     * @param group   任务分组
     * @param e       错误信息
     */
    private void setErrorLog(String msg, String jobName, String group, Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(msg).append("任务名称：").append(jobName).append("  分组：").append(group);
        log.error(stringBuilder.toString(), e);
    }


    /**
     * 获取所有的任务
     *
     * @return 任务列表
     */
    public Set<String> getTaskList() {
        return jobMap.keySet();
    }

    /**
     * 从执行任务列表删除任务
     */
    public static void delTask(String jobCode) {
        JobDetail jobDetail = tasks.get(jobCode);
        Optional.ofNullable(jobDetail).ifPresent(s->{
            tasks.remove(jobCode);
        });

         System.err.println("任务列表"+tasks);
    }

}
