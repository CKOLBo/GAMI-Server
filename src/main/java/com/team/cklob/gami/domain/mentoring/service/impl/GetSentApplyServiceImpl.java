package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import com.team.cklob.gami.domain.mentoring.presentation.dto.response.GetSentApplyResponse;
import com.team.cklob.gami.domain.mentoring.repository.ApplyRepository;
import com.team.cklob.gami.domain.mentoring.service.GetSentApplyService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetSentApplyServiceImpl implements GetSentApplyService {

    private final ApplyRepository applyRepository;
    private final MemberUtil memberUtil;

    @Override
    @Transactional(readOnly = true)
    public List<GetSentApplyResponse> execute() {
        Member mentee = memberUtil.getCurrentMember();

        List<Apply> applyList = applyRepository.findAllByMenteeIdAndApplyStatusOrderByCreatedAtDesc(mentee.getId(), ApplyStatus.PENDING);

        return applyList.stream()
                .map(apply -> {
                    Member mentor = apply.getMentor();
                    return GetSentApplyResponse.builder()
                            .applyId(apply.getId())
                            .mentorId(mentor.getId())
                            .name(mentor.getName())
                            .applyStatus(apply.getApplyStatus())
                            .createdAt(apply.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
