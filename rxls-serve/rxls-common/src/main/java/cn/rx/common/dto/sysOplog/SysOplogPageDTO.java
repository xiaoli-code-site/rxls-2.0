package cn.rx.common.dto.sysOplog;

import cn.rx.common.dto.PageDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 用户分页查询
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class SysOplogPageDTO extends PageDTO {

    /**
     * 等级
     */
    private Integer leve;

    public void setLeve(Integer leve) {
        this.leve = leve;
    }
}
