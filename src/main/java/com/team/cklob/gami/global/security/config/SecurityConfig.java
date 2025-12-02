package com.team.cklob.gami.global.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.cklob.gami.global.filter.ExceptionFilter;
import com.team.cklob.gami.global.filter.JwtFilter;
import com.team.cklob.gami.global.security.handler.JwtAccessDeniedHandler;
import com.team.cklob.gami.global.security.handler.JwtAuthenticationEntryPoint;
import com.team.cklob.gami.global.security.jwt.JwtProvider;
import com.team.cklob.gami.global.security.jwt.TokenParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final TokenParser tokenParser;
    private final ObjectMapper objectMapper;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint  jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

                .exceptionHandling(config ->
                        config.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .sessionManagement(config ->
                        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )


                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                                //auth
                                .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/email/send-code").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/email/verification-code").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/api/auth/reissue").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/auth/signout").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/auth/password").permitAll()

                                //member
                                .requestMatchers(HttpMethod.GET, "/api/member/{id}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/member").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/member/all").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/member/major").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/member/password").authenticated()
                                .anyRequest().denyAll()
                )

                .addFilterBefore(new ExceptionFilter(objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(jwtProvider, tokenParser), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
