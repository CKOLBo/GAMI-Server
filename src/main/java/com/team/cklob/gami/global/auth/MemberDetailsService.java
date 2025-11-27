package com.team.cklob.gami.global.auth;

import com.team.cklob.gami.domain.member.exception.NotFoundMemberDetail;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(MemberDetails::new)
                .orElseThrow(NotFoundMemberDetail::new);
    }
}
