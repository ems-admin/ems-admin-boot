package com.ems.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2022-01-19 21:16
 **/
@Component
@RequiredArgsConstructor
public final class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
    * @Description: 保存值
    * @Param: [key, value, time, timeUnit]
    * @return: void
    * @Author: starao
    * @Date: 2021/12/28
    */
    public void setValue(String key, Object value, Long time, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
    * @Description: 获取值
    * @Param: [key]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/12/28
    */
    public String getValue(String key){
        Object object = redisTemplate.opsForValue().get(key);
        if (object == null){
            return null;
        } else {
            return object.toString();
        }

    }

    /**
    * @Description: 删除对应key的值
    * @Param: [key]
    * @return: void
    * @Author: starao
    * @Date: 2022/1/16
    */
    public void clearValue(String key){
        redisTemplate.delete(key);
    }

}
