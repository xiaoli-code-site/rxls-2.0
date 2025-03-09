package cn.rx.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 */
@Component("testTask")
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        System.err.println("定时任务testJob执行");
    }
}
