package com.team.cklob.gami.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> stringRedisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;


    public void setCode(String key, String value, Long milliSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MINUTES);
    }

    public void pendingCode(String key, String value, Long milliSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setVerifyStatus(String key, String value, Long milliSeconds) {
        stringRedisTemplate.opsForValue().setIfAbsent(key, value, milliSeconds, TimeUnit.MINUTES);
    }

    public void setBlackList(String key, Object value, Long milliSeconds) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
        redisBlackListTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public Object getBlackList(String key) {
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean hasKeyBlackList(String key) {
        return redisBlackListTemplate.hasKey(key);
    }

    public boolean deleteBlackList(String key) {
        return redisBlackListTemplate.delete(key);
    }
}
