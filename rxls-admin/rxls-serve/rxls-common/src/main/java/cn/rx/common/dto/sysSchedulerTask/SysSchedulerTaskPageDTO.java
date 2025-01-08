package cn.rx.common.dto.sysSchedulerTask;

import cn.rx.common.dto.PageDTO;
import cn.rx.common.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 分页查询定时任务
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class SysSchedulerTaskPageDTO extends PageDTO {
    /**
     * 发布人
     */
    private String username;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 分组名称
     */
    private String groupName;

    public void setUsername(String username) {
        this.username = StrUtil.validateValue(username);
    }

    public void setJobName(String jobName) {
        this.jobName =  StrUtil.validateValue(jobName);
    }

    public void setGroupName(String groupName) {
        this.groupName =  StrUtil.validateValue(groupName);
    }
}
