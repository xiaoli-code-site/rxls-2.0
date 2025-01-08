package cn.rx.db.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 定时任务(SysSchedulerTaskVO)表实体类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_scheduler_task")
public class SysSchedulerTask {
    @TableId(type = IdType.AUTO)
    /**
     * 主键
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
     * 创建用户id
     */
    private Long userId;
    /**
     * 开始时间 空或时间小于当前时间表示立即执行
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 模式 1为corn表达式  0 为普通模式
     */
    private Integer model;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
