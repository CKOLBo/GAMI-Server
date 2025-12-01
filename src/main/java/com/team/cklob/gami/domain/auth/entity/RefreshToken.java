package com.team.cklob.gami.domain.auth.entity;


import com.team.cklob.gami.global.security.jwt.JwtProvider;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "gami_refreshToken", timeToLive = JwtProvider.REFRESH_TOKEN_TIME)
@Getter
@Builder
public class RefreshToken {

    @Id
    private String email;

    @Indexed
    private String token;
}
