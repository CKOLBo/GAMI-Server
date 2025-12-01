package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.exception.EmailAlreadyExistsException;
import com.team.cklob.gami.domain.auth.exception.UnverifiedEmailException;
import com.team.cklob.gami.domain.auth.presentation.dto.request.SignUpRequest;
import com.team.cklob.gami.domain.auth.service.SignUpService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.entity.constant.MemberRole;
import com.team.cklob.gami.domain.member.repository.MemberDetailRepository;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.global.redis.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    private static final String VERIFY_TIME_PREFIX = "auth:verify:time:";

    @Override
    @Transactional
    public void execute(SignUpRequest request) {
        validateDuplicateEmail(request.email());

        String verifyKey = VERIFY_TIME_PREFIX + request.email();
        if (!redisUtil.hasKey(verifyKey)) {
            throw new UnverifiedEmailException();
        }

        Member member = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .role(MemberRole.ROLE_STUDENT)
                .build();

        memberRepository.save(member);

        MemberDetail memberDetail = MemberDetail.builder()
                .member(member)
                .generation(request.generation())
                .gender(request.gender())
                .major(request.major())
                .build();

        memberDetailRepository.save(memberDetail);
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
    }
}
