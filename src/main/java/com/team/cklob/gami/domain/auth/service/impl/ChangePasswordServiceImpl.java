package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.exception.EmailAlreadyExistsException;
import com.team.cklob.gami.domain.auth.exception.NotFoundUserException;
import com.team.cklob.gami.domain.auth.exception.NotFoundVerifyCodeException;
import com.team.cklob.gami.domain.auth.exception.NotMatchedCodeException;
import com.team.cklob.gami.domain.auth.presentation.dto.request.ChangePasswordRequest;
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

    @Override
    @Transactional
    public void execute(ChangePasswordRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(NotFoundMemberException::new);

        String key = EMAIL_AUTH_PREFIX +  request.email();

        if (!redisUtil.hasKey(key)) {
            throw new NotFoundVerifyCodeException();
        }

        if (!redisUtil.getValue(key).equals(request.code())) {
            throw new NotMatchedCodeException();
        }

        member.changePassword(passwordEncoder.encode(request.newPassword()));
    }
}
