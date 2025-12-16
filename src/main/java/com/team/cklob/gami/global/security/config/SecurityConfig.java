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

                                // AUTH
                                .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/email/send-code").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/email/verification-code").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/api/auth/reissue").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/auth/signout").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/auth/password").permitAll()

                                // MEMBER
                                .requestMatchers(HttpMethod.GET, "/api/member/{id}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/member").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/member/all").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/member/major").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/member/password").authenticated()

                                // POST
                                .requestMatchers(HttpMethod.POST, "/api/post").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/post/{postId}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/post/{postId}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/post", "/api/post/{postId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/post/images").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/post/images").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/post/summary/{postId}").authenticated()

                                // COMMENT
                                .requestMatchers(HttpMethod.POST, "/api/post/*/comment").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/post/*/comment").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/post/comment/*").authenticated()

                                // POST LIKE
                                .requestMatchers(HttpMethod.POST, "/api/post/*/like").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/post/*/like").authenticated()

                                // REPORT
                                .requestMatchers(HttpMethod.POST, "/api/report").authenticated()

                                // ADMIN
                                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ROLE_ADMIN")

                                // HEALTH
                                .requestMatchers(HttpMethod.GET, "/api/health").permitAll()

                                // MENTORING
                                .requestMatchers(HttpMethod.POST, "/api/mentoring/apply/{mentorId}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/mentoring/apply/sent").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/mentoring/apply/received").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/mentoring/mentor/all").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/mentoring/random").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/mentoring/apply/{id}").authenticated()

                                // CHAT
                                .requestMatchers(HttpMethod.GET, "/api/chat/rooms").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/chat/{roomId}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/chat/{roomId}/messages").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/chat/rooms/{roomId}/leave").authenticated()

                                // WEBSOCKET
                                .requestMatchers("/ws/**").permitAll()
                                .anyRequest().denyAll()
                )

                .addFilterBefore(new ExceptionFilter(objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(jwtProvider, tokenParser), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}