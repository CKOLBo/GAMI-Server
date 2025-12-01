package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.repository.RefreshTokenRepository;
import com.team.cklob.gami.domain.auth.service.SignOutService;
import com.team.cklob.gami.global.redis.RedisUtil;
import com.team.cklob.gami.global.security.jwt.JwtProvider;
import com.team.cklob.gami.global.util.MemberUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignOutServiceImpl implements SignOutService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberUtil memberUtil;
    private final RedisUtil redisUtil;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void execute(String accessToken) {
        String email = memberUtil.getCurrentMember().getEmail();
        refreshTokenRepository.deleteById(email);

        Long expirationMillis = jwtProvider.getExpiration(accessToken);
        redisUtil.setBlackList(accessToken, "BLACKLIST", expirationMillis);
    }
}
