package cn.rx.common.vo.admin.sysAdmin;


import cn.rx.common.vo.admin.sysUser.SysUserVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysAdminVO extends SysUserVO {

    /**
     * 账户
     */
    private String username;


}
