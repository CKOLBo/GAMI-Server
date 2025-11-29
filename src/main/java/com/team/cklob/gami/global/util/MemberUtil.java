package com.team.cklob.gami.global.util;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberException;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.global.auth.MemberDetails;
import com.team.cklob.gami.global.security.exception.InvalidMemberPrincipalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof MemberDetails memberDetails) {
            String email = memberDetails.getUsername();

            return memberRepository.findByEmail(email)
                    .orElseThrow(NotFoundMemberException::new);
        }
        throw new InvalidMemberPrincipalException();
    }
}
