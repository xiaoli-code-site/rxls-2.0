package cn.rx.common.vo.admin.sysSchedulerTask;


import cn.rx.common.vo.admin.sysUser.SysUserVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 定时任务
 */
@Data
public class SysSchedulerTaskVO {
    /**
     * 主键
      */
    private Integer schedulerTaskId;

    /**
     * 用户信息
     */
    private SysUserVO user;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
