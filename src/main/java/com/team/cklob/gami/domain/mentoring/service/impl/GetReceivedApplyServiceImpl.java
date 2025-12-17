package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import com.team.cklob.gami.domain.mentoring.dto.response.GetReceivedApplyResponse;
import com.team.cklob.gami.domain.mentoring.repository.ApplyRepository;
import com.team.cklob.gami.domain.mentoring.service.GetReceivedApplyService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetReceivedApplyServiceImpl implements GetReceivedApplyService {

    private final ApplyRepository applyRepository;
    private final MemberUtil  memberUtil;

    @Override
    @Transactional(readOnly = true)
    public List<GetReceivedApplyResponse> execute() {
        Member mentor = memberUtil.getCurrentMember();

        List<Apply> applyList = applyRepository.findAllByMenteeIdAndApplyStatusWithMentor(mentor.getId(), ApplyStatus.PENDING);


        return applyList.stream()
                .map(apply -> {
                    Member mentee = apply.getMentee();
                    return GetReceivedApplyResponse.builder()
                            .applyId(apply.getId())
                            .menteeId(mentee.getId())
                            .name(mentee.getName())
                            .applyStatus(apply.getApplyStatus())
                            .createdAt(apply.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
