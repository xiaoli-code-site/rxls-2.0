package cn.rx.common.vo.sysLogin;

import cn.rx.common.vo.sysMenu.SysMenuVO;
import cn.rx.common.vo.sysUser.SysUserVO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * 管理员登录
 */
@Data
@AllArgsConstructor
public class SysLoginAdminVO {
    /**
     * 用户信息
     */
    private SysUserVO user;

    /**
     * 用户名
     */
    private String username;

    /**
     * token令牌
     */
    private String token;

    /**
     * 权限路由
     */
    private List<SysMenuVO> menus;
}
