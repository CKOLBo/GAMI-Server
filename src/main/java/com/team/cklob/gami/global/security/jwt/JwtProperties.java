package com.team.cklob.gami.global.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@AllArgsConstructor
@Getter
public class JwtProperties {
    private String accessSecret;
    private String refreshSecret;
}
