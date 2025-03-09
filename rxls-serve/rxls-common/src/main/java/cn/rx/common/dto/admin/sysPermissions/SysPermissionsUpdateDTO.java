package cn.rx.common.dto.admin.sysPermissions;


import cn.rx.common.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 角色更新
 */
@Data
public class SysPermissionsUpdateDTO {
    /**
     * 权限id
     */
    @NotNull(message = "{idNotEmpty}")
    @Min(value = 1, message = "{illegalId}")
    private Integer permissionsId;

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

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }

    public void setDescription(String description) {
        this.description = StrUtil.validateValue(description);
    }

    public void setUrl(String url) {
        this.url = StrUtil.validateValue(url);
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
