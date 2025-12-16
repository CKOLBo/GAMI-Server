package com.team.cklob.gami.global.security.jwt;

import com.team.cklob.gami.global.auth.MemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.team.cklob.gami.global.filter.JwtFilter.AUTHORIZATION_HEADER;

@Component
@RequiredArgsConstructor
public class TokenParser {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;

    private static final String BEARER_TYPE = "Bearer ";

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtProvider.getAccessKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        String email = claims.getSubject();
        String authority = claims.get("auth", String.class);

        UserDetails principal = memberDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(
                principal,
                "",
                List.of(new SimpleGrantedAuthority(authority))
        );
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(BEARER_TYPE.length());
        }
        return null;
    }

    public String parseRefreshToken(String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith(BEARER_TYPE)) {
            return refreshToken.substring(BEARER_TYPE.length());
        }
        return null;
    }
}
