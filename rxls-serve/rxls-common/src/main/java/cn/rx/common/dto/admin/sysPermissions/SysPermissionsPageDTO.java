package cn.rx.common.dto.admin.sysPermissions;

import cn.rx.common.dto.admin.PageDTO;
import cn.rx.common.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 权限分页查询
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class SysPermissionsPageDTO extends PageDTO {

    /**
     * 描述
     */
    private String description;

    /**
     * 权限值
     */
    private String url;

    /**
     * 状态
     */
    private Integer state;

    public void setDescription(String description) {
        this.description = StrUtil.validateValue(description);
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
