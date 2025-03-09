package cn.rx.common.vo.login;


import cn.rx.common.vo.admin.sysUser.SysUserVO;
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

    public LoginUserVO(){
        this.token = "";
        this.user = new SysUserVO();
    }


}
