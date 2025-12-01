package com.team.cklob.gami.global.security.jwt;

import com.team.cklob.gami.domain.member.entity.constant.MemberRole;
import com.team.cklob.gami.global.redis.RedisUtil;
import com.team.cklob.gami.global.security.exception.ExpiredTokenException;
import com.team.cklob.gami.global.security.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_TIME =  60L * 60 * 24;
    public static final long REFRESH_TOKEN_TIME = 60L * 60 * 24 * 7;

    private final JwtProperties jwtProperties;
    private final RedisUtil redisUtil;

    public Key getAccessKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8));
    }

    public Key getRefreshKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getAccessKey()).build().parseClaimsJws(token);
            return !redisUtil.hasKeyBlackList(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getRefreshKey()).build().parseClaimsJws(token);
            return !redisUtil.hasKeyBlackList(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public String validateAndGetSubject(String token) {
        if (!validateAccessToken(token)) {
            throw new InvalidTokenException();
        }
        return Jwts.parserBuilder()
                .setSigningKey(getAccessKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateAccessToken(String email, MemberRole role) {
        Date expiry = new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME * 1000);

        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, "ROLE_" + role.name())
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date expiry = new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME * 1000);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expiry)
                .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public long getExpiration(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getAccessKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().getTime() - System.currentTimeMillis();
    }

    public long getAccessTokenTime() {
        return ACCESS_TOKEN_TIME;
    }

    public long getRefreshTokenTime() {
        return REFRESH_TOKEN_TIME;
    }
}
