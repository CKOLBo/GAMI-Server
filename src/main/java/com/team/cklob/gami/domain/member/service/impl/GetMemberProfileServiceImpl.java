package com.team.cklob.gami.domain.member.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberDetailException;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberException;
import com.team.cklob.gami.domain.member.presentation.dto.response.GetMemberProfileResponse;
import com.team.cklob.gami.domain.member.repository.MemberDetailRepository;
import com.team.cklob.gami.domain.member.repository.MemberRepository;

import com.team.cklob.gami.domain.member.service.GetMemberProfileService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMemberProfileServiceImpl implements GetMemberProfileService {

    private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public GetMemberProfileResponse execute(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        MemberDetail memberDetail = memberDetailRepository.findById(memberId)
                .orElseThrow(NotFoundMemberDetailException::new);

        return new GetMemberProfileResponse(
                member.getId(),
                member.getName(),
                memberDetail.getGender(),
                memberDetail.getGeneration(),
                memberDetail.getMajor()
        );
    }
}
