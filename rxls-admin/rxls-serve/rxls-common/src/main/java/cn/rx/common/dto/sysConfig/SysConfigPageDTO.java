package cn.rx.common.dto.sysConfig;

import cn.rx.common.dto.PageDTO;
import cn.rx.common.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * 分页查询配置
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class SysConfigPageDTO extends PageDTO {
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private Integer state;

    public void setState(Integer state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = StrUtil.validateValue(description);
    }
}
