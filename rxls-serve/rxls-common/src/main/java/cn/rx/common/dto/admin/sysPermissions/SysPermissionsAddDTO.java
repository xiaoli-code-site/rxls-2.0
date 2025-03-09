package cn.rx.common.dto.admin.sysPermissions;

import cn.rx.common.util.StrUtil;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色新增
 */
@Data
public class SysPermissionsAddDTO {

    /**
     * 描述
     */
    @NotBlank(message = "{describeNoEmpty}")
    private String description;

    /**
     * 权限值
     */
    @NotBlank(message = "{permissionsUrlNoEmpty}")
    private String url;


    public void setDescription(String description) {
        this.description = StrUtil.validateValue(description);
    }

    public void setUrl(String url) {
        this.url =  StrUtil.validateValue(url);
    }

}
