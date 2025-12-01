package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.entity.RefreshToken;
import com.team.cklob.gami.domain.auth.exception.NotFoundUserException;
import com.team.cklob.gami.domain.auth.exception.UnauthorizedException;
import com.team.cklob.gami.domain.auth.presentation.dto.responnse.TokenResponse;
import com.team.cklob.gami.domain.auth.repository.RefreshTokenRepository;
import com.team.cklob.gami.domain.auth.service.ReissueTokenService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.global.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReissueTokenServiceImpl implements ReissueTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public TokenResponse execute(String refreshToken) {
        RefreshToken savedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(UnauthorizedException::new);

        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new UnauthorizedException();
        }

        Member member = memberRepository.findByEmail(savedToken.getEmail())
                .orElseThrow(NotFoundUserException::new);
        String newAccessToken = jwtProvider.generateAccessToken(member.getEmail(), member.getRole());
        String newRefreshToken = jwtProvider.generateRefreshToken(member.getEmail());

        RefreshToken updatedToken = RefreshToken.builder()
                .email(savedToken.getEmail())
                .token(newRefreshToken)
                .build();

        refreshTokenRepository.save(updatedToken);

        return new TokenResponse(
                newAccessToken,
                newRefreshToken,
                LocalDateTime.now().plusSeconds(jwtProvider.getAccessTokenTime()),
                LocalDateTime.now().plusSeconds(jwtProvider.getRefreshTokenTime())
        );
    }
}
