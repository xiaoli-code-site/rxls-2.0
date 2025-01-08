package cn.rx.common.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 验证码工具类
 */
public class CaptchaUtil {

    /**
     * 普通验证码类型
     */
    public final static String CACHE = "cache";

    /**
     * 校验验证码类型
     */
    public final static String VERIFICATION = "verification";


    // 创建一个缓存实例,普通验证码
    private static final Cache<String, String> cache = Caffeine.newBuilder().initialCapacity(5)  // 初始容量为5
            .maximumSize(1000)     // 最大容量为10
            .expireAfterAccess(60, TimeUnit.SECONDS) // 在最后一次访问后的60秒内过期
            .build();

    // 创建一个缓存实例,校验验证码
    private static final Cache<String, String> verificationCache = Caffeine.newBuilder().initialCapacity(5)  // 初始容量为5
            .maximumSize(1000)     // 最大容量为10
            .expireAfterAccess(60 * 5, TimeUnit.SECONDS) // 在最后一次访问后的60秒内过期
            .build();

    /**
     * 存入验证码
     *
     * @param key   验证码key
     * @param value 验证码的值
     * @param type  验证码类型
     */
    public static void setCache(String key, String value, String type) {
        if (CACHE.equals(type)) {
            cache.put(key, value);
        } else if (VERIFICATION.equals(type)) {
            verificationCache.put(key, value);
        }
    }

    /**
     * 获取验证码
     *
     * @param key  验证码key
     * @param type 验证码类型
     * @return 验证码的值
     */
    public static String getCache(String key, String type) {
        if (CACHE.equals(type)) {
            return cache.getIfPresent(key);
        } else if (VERIFICATION.equals(type)) {
            return verificationCache.getIfPresent(key);
        }
        return null;
    }

    /**
     * 清除特定验证码的方法
     *
     * @param key  验证码key
     * @param type 验证码类型
     */
    public static void removeVerificationCode(String key, String type) {
        if (CACHE.equals(type)) {
            cache.invalidate(key);
        } else if (VERIFICATION.equals(type)) {
            verificationCache.invalidate(key);
        }
    }

    /**
     * 验证码是否正确
     *
     * @param key   验证码key
     * @param value 验证码值
     * @param type  验证码类型
     */
    public static Boolean verify(String key, String value, String type) {
        String cacheValue = getCache(key, type);
        if (Objects.isNull(cacheValue) || !value.equals(cacheValue)) {
            return false;
        }
        removeVerificationCode(key, type);
        return true;
    }

}
