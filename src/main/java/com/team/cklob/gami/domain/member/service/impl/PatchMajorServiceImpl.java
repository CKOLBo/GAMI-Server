package com.team.cklob.gami.domain.member.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberDetailException;
import com.team.cklob.gami.domain.member.dto.request.PatchMajorRequest;
import com.team.cklob.gami.domain.member.repository.MemberDetailRepository;
import com.team.cklob.gami.domain.member.service.PatchMajorService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatchMajorServiceImpl implements PatchMajorService {

    private final MemberUtil memberUtil;
    private final MemberDetailRepository memberDetailRepository;

    @Override
    @Transactional
    public void execute(PatchMajorRequest request) {
        Member member = memberUtil.getCurrentMember();

        MemberDetail memberDetail = memberDetailRepository.findById(member.getId())
                .orElseThrow(NotFoundMemberDetailException::new);

        memberDetail.updateMajor(request.major());
    }
}
