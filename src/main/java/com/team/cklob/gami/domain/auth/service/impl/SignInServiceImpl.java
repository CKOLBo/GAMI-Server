package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.entity.RefreshToken;
import com.team.cklob.gami.domain.auth.exception.NotFoundUserException;
import com.team.cklob.gami.domain.auth.exception.UnauthorizedException;
import com.team.cklob.gami.domain.auth.dto.request.SignInRequest;
import com.team.cklob.gami.domain.auth.dto.responnse.TokenResponse;
import com.team.cklob.gami.domain.auth.repository.RefreshTokenRepository;
import com.team.cklob.gami.domain.auth.service.SignInService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.global.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public TokenResponse execute(SignInRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(NotFoundUserException::new);

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new UnauthorizedException();
        }

        String accessToken = jwtProvider.generateAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtProvider.generateRefreshToken(member.getEmail());

        refreshTokenRepository.save(RefreshToken.builder()
                .email(member.getEmail())
                .token(refreshToken)
                .build());

        return new TokenResponse(
                accessToken,
                refreshToken,
                LocalDateTime.now().plusSeconds(jwtProvider.getAccessTokenTime()),
                LocalDateTime.now().plusSeconds(jwtProvider.getRefreshTokenTime())
        );
    }
}
