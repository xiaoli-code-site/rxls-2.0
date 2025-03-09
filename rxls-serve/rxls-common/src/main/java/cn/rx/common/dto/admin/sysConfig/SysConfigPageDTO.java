package cn.rx.common.dto.admin.sysConfig;

import cn.rx.common.dto.admin.PageDTO;
import cn.rx.common.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
