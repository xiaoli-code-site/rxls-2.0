package cn.rx.common.dto.sysConfig;

import cn.rx.common.util.StrUtil;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 更新配置
 */
@Getter
public class SysConfigUpdateDTO {
    /**
     * 主键
     */
    @NotNull(message = "{idNotEmpty}")
    @Min(value = 1,message = "{illegalId}")
    private Integer id;

    /**
     * 值
     */
    @NotBlank(message = "{valueNoEmpty}")
    private String sysValue;
    /**
     * 描述
     */
    @NotBlank(message = "{describeNoEmpty}")
    private String description;

    /**
     * 状态
     */
    @NotNull(message = "{stateNotEmpty}")
    @Min(value = 0, message = "{illegalState}")
    @Max(value = 1, message = "{illegalState}")
    private Integer state;

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = StrUtil.validateValue(description);
    }

}
