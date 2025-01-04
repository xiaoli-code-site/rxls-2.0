package cn.rx.common.util;


import cn.rx.common.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Jwt工具类
 * @author 不想说话而已
 */
public class JwtUtil {



    /**
     * 创建令牌
     * @param iss   签发者
     * @param subject      用户标识
     * @param info     存储信息
     * @param beforeDuration  生效时间
     * @param duration 有效时长 单位毫秒
     */
    public static String createToken(String iss,String subject, Map<String, Object> info,Long beforeDuration, Long duration) {
        return Jwts.builder()
                .setClaims(info)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setNotBefore(new Date(System.currentTimeMillis() + beforeDuration))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .setId(UUID.randomUUID().toString())
                .setIssuer(iss)
                .setIssuedAt(new Date())
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, JwtConstant.SECRET_KEY).compact();
    }

    /**
     * 令牌解析
     *
     * @param token 令牌
     */
    public static Claims parseToken(String token) {
        if(Objects.isNull(token) || "".equals(token)){
            return null;
        }
        try {
            return Jwts.parser().setSigningKey(JwtConstant.SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
