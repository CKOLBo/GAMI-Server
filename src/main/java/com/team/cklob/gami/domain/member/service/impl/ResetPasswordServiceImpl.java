package com.team.cklob.gami.domain.member.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.exception.NotMatchedPasswordException;
import com.team.cklob.gami.domain.member.presentation.dto.request.ResetPasswordRequest;
import com.team.cklob.gami.domain.member.service.ResetPasswordService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;

    @Override
    @Transactional
    public void execute(ResetPasswordRequest request) {
        Member member = memberUtil.getCurrentMember();

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new NotMatchedPasswordException();
        }

        member.changePassword(passwordEncoder.encode(request.newPassword()));
    }
}
