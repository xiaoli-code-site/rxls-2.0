package cn.rx.quartz.config;

import cn.rx.common.enums.CommonStateEnum;
import cn.rx.db.entity.SysSchedulerTask;
import cn.rx.db.mapper.SysSchedulerTaskMapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 全局任务作业监听器
 */
@Component
public class GlobalTriggerExecutionListener implements JobListener {

    private final Scheduler scheduler;

    private final SysSchedulerTaskMapper sysSchedulerTaskMapper;

    public GlobalTriggerExecutionListener(Scheduler scheduler,SysSchedulerTaskMapper sysSchedulerTaskMapper) {
        this.scheduler = scheduler;
        this.sysSchedulerTaskMapper = sysSchedulerTaskMapper;
    }

    @Override
    public String getName() {
        return "GlobalTriggerExecutionListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @SneakyThrows
    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        Date nextFireTime = jobExecutionContext.getNextFireTime();
        //执行任务是否结束
        if(Objects.isNull(nextFireTime)){
            scheduler.deleteJob(jobExecutionContext.getJobDetail().getKey());
            LambdaUpdateWrapper<SysSchedulerTask> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(SysSchedulerTask::getJobCode,jobExecutionContext.getTrigger().getKey().getName());
            SysSchedulerTask schedulerTask = new SysSchedulerTask();
            schedulerTask.setState(CommonStateEnum.ERROR.code);
            sysSchedulerTaskMapper.update(schedulerTask,wrapper);
        }
    }

}