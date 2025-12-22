package com.team.cklob.gami.global.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.cklob.gami.domain.chat.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final int MAX_CACHED_MESSAGES = 200;

    public void setCode(String key, String value, Long milliSeconds) {
        redisTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MINUTES);
    }

    public void pendingCode(String key, String value, Long milliSeconds) {
        redisTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public String getValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

    public void setVerifyStatus(String key, String value, Long milliSeconds) {
        redisTemplate.opsForValue().setIfAbsent(key, value, milliSeconds, TimeUnit.MINUTES);
    }

    public void setBlackList(String key, Object value, Long milliSeconds) {
        redisTemplate.opsForValue().set(key, value, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public boolean deleteValue(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public Object getBlackList(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public void appendRecentMessage(ChatMessageResponse response, String key) {
        try {
            String json = objectMapper.writeValueAsString(response);
            redisTemplate.opsForList().leftPush(key, json);
            redisTemplate.opsForList().trim(key, 0, MAX_CACHED_MESSAGES - 1);
        } catch (Exception e) {
            log.error("Failed to append recent message", e);
        }
    }
}
