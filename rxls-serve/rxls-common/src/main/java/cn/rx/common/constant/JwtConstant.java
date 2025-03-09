package cn.rx.common.constant;

/**
 * JWT常量
 */
public class JwtConstant {


    /**
     * 请求头 token标识
     */
    public static final String TOKEN = "Authorization";

    /**
     * 角色标识
     */
    public static final String ROLE = "role";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * 权限标识
     */
    public static final String PERMISSIONS = "Permissions";


    /**
     * 创建jwt的秘钥key
     */
    public static final String  SECRET_KEY = "auth";


    /**
     * 微信 iss签发者
     */
    public static final String  WX_ISS = "rx_wx_iss";

    /**
     * 网站 iss签发者
     */
    public static final String  ADMIN_ISS = "rx_admin_iss";

    /**
     * app iss签发者
     */
    public static final String  APP_ISS = "rx_app_iss";

}
