package com.team.cklob.gami.global.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.cklob.gami.domain.chat.presentation.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> stringRedisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;
    private final ObjectMapper objectMapper;


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
        redisBlackListTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public boolean deleteValue(String key) {
        return stringRedisTemplate.delete(key);
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

    public void appendRecentMessage(ChatMessageResponse response, String key) {
        try {
            String json = objectMapper.writeValueAsString(response);

            stringRedisTemplate.opsForList().leftPush(key, json);
            stringRedisTemplate.opsForList().trim(key, 0, 199);
        } catch (Exception e) {
            log.error("Failed to append recent message to cache for key: {}", key, e);
        }
    }
}
