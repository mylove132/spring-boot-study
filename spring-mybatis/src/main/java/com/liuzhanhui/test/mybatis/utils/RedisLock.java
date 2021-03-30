package com.liuzhanhui.test.mybatis.utils;

import com.alibaba.druid.util.StringUtils;
import com.liuzhanhui.test.mybatis.service.IRedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {

    @Autowired
    private IRedisCacheService redisCacheService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean lock (String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String currentValue = redisTemplate.opsForValue().get(key).toString();
        if (!StringUtils.isEmpty(currentValue) && System.currentTimeMillis() > Long.parseLong(currentValue)) {
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value).toString();
            if (StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    public void unlock (String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key).toString();
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisCacheService.del(key);
            }
        }catch (Exception e) {
            throw new RuntimeException("解锁失败, {}", e);
        }
    }
}
