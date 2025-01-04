package cn.rx.common.dto.sysConfig;

import cn.rx.common.util.StrUtil;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * 新增配置
 */
@Data
@Getter
public class SysConfigAddDTO {

    /**
     * key名
     */
    @NotBlank(message = "{keyNoEmpty}")
    private String sysKey;
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

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }


    public void setDescription(String description) {
        this.description = StrUtil.validateValue(description);
    }
}
