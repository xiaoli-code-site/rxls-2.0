package cn.rx.quartz.entity;

import cn.rx.quartz.enums.ErroneousEnum;
import lombok.Data;
import org.quartz.ScheduleBuilder;

import java.util.Date;

/**
 * 定时任务对象
 */
@Data
public class SchedulerTask {

    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 表达式:
     * cron模式下：  cron表达式
     * 普通模式下：  次数+频率
     */
    private String expression;
    /**
     * 单位
     * corn模式下 ：指定误点模式
     * 普通模式下： 时间单位
     */
    private String unit;
    /**
     * 任务编号
     */
    private String jobCode;


    /**
     * 模式 1为corn表达式  0 为普通模式
     */
    private Integer model;
    /**
     * 开始时间 空或时间小于当前时间表示立即执行
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 误点枚举类型
     */
    private ErroneousEnum erroneousEnum;

    /**
     * 触发器
     */
    private ScheduleBuilder scheduleBuilder;

}
