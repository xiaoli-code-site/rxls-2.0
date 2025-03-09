package cn.rx.common.dto.admin.sysRole;

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
public class SysRoleAddDTO {

    /**
     * 角色名
     */
    @NotBlank(message = "{nicknameNoEmpty}")
    private String roleName;

    /**
     * 权限值
     */
    @NotNull(message = "{roleValueNoEmpty}")
    @Min(value = 1, message = "{illegalRoleValue}")
    @Max(value = 99999, message = "{illegalRoleValue}")
    private Integer roleValue;

    /**
     * 权限ID列表
     */
    private List<Integer> permissions;
}
