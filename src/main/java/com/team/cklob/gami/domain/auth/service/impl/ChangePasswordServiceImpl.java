package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.exception.NotFoundVerifyCodeException;
import com.team.cklob.gami.domain.auth.dto.request.ChangePasswordRequest;
import com.team.cklob.gami.domain.auth.service.ChangePasswordService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberException;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.global.redis.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    private static final String EMAIL_AUTH_PREFIX = "auth:email:";
    private static final String VERIFY_TIME_PREFIX = "auth:verify:time:";

    @Override
    @Transactional
    public void execute(ChangePasswordRequest request) {
        String verifyKey = VERIFY_TIME_PREFIX + request.email();
        if (!redisUtil.hasKey(verifyKey)) {
            throw new NotFoundVerifyCodeException();
        }

        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(NotFoundMemberException::new);

        member.changePassword(passwordEncoder.encode(request.newPassword()));
        redisUtil.deleteValue(verifyKey);
    }
}
