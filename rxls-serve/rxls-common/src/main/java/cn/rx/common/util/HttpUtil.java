package cn.rx.common.util;


import cn.rx.common.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * http工具类
 * @author 不想说话而已
 */
public class HttpUtil {


    /**
     * 获取http信息
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpInfo() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }


    /**
     * 获取令牌包含的用户信息
     * @return 令牌信息
     */
    public static Claims getUserLoginInfo() {
        HttpServletRequest request = getHttpInfo();
        String token = request.getHeader(JwtConstant.TOKEN);
        if (Objects.isNull(token) || "".equals(token)) {
            return null;
        }

        Claims claims = JwtUtil.parseToken(token);
        if (Objects.isNull(claims)) {
            return null;
        }
        return claims;
    }
}
