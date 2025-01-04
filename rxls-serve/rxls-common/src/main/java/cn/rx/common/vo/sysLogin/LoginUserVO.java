package cn.rx.common.vo.sysLogin;

import cn.rx.common.vo.sysUser.SysUserVO;
import lombok.Data;

/**
 * 登录
 */
@Data
public class LoginUserVO {
    /**
     * 登录令牌
     */
    private String token;
    /**
     * 用户信息
     */
    private SysUserVO user;
}
