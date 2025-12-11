package com.team.cklob.gami.domain.member.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberDetailException;
import com.team.cklob.gami.domain.member.dto.response.GetMemberProfileResponse;
import com.team.cklob.gami.domain.member.repository.MemberDetailRepository;
import com.team.cklob.gami.domain.member.service.GetMyProfileService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyProfileServiceImpl implements GetMyProfileService {

    private final MemberDetailRepository  memberDetailRepository;
    private final MemberUtil memberUtil;

    @Override
    @Transactional(readOnly = true)
    public GetMemberProfileResponse execute() {
        Member member = memberUtil.getCurrentMember();

        MemberDetail memberDetail = memberDetailRepository.findById(member.getId())
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
