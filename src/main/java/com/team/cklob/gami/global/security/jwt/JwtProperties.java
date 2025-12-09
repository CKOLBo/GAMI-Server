package com.team.cklob.gami.global.security.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String accessSecret;
    private final String refreshSecret;

    public JwtProperties(String accessSecret, String refreshSecret) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
    }
}
