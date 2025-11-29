package com.team.cklob.gami.global.filter;

import com.team.cklob.gami.global.security.jwt.JwtProvider;
import com.team.cklob.gami.global.security.jwt.TokenParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";


    private final JwtProvider jwtProvider;
    private final TokenParser  tokenParser;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = tokenParser.resolveToken(request);

        if (StringUtils.hasText(jwt) && jwtProvider.validateAccessToken(jwt)) {
            Authentication authentication = tokenParser.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
