package cn.rx.common.dto.sysSchedulerTask;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 更新定时任务
 */
@Data
public class SysSchedulerTaskUpdateDTO {
    /**
     * 主键id
     */
    private Integer schedulerTaskId;

    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 1为普通模式， 0为corn表达式
     */
    private Integer model;
    /**
     * 时间表达式:
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
     * 开始时间 空或时间小于当前时间表示立即执行
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 状态
     */
    private Integer state;
}
