package cn.rx.core.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务
 */
@Component
public class RedisService {
    @Resource
    private  RedisTemplate<String, Object> redisTemplate;


    @Value("${redis.open}")
    private Boolean open;

    /**
     * 存储 key-value
     *
     * @param key   key
     * @param value 存储的值
     */
    public void setValue(String key, Object value) {
        if(!open){
            return;
        }
        if (!(value instanceof String)) {
            value = JSON.toJSONString(value);
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存储 key-value
     *
     * @param key   key
     * @param id   唯一标识
     * @param value 存储的值
     * @param time  有效时长
     * @param unit  时长单位
     */
    public void setValue(String key,Object id, Object value, long time, TimeUnit unit) {
        if(!open){
            return;
        }
        if (!(value instanceof String)) {
            value = JSON.toJSONString(value);
        }
        redisTemplate.opsForValue().set(key.concat(String.valueOf(id)), value, time, unit);
    }

    /**
     * 获取 key-value
     * @param key key
     * @param id   唯一标识
     */
    public String getValue(String key,Object id) {
        if(!open){
           return null;
        }
        Object str = redisTemplate.opsForValue().get(key.concat((String) id));
        return String.valueOf(str);
    }

    /**
     * 删除
     *
     * @param key key
     */
    public Boolean delete(String key) {
        if(!open){
            return null;
        }
        return redisTemplate.delete(key);
    }

}
