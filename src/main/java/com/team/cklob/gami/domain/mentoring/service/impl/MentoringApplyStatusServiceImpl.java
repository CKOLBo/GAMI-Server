package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import com.team.cklob.gami.domain.mentoring.exception.ApplyNotFoundException;
import com.team.cklob.gami.domain.mentoring.presentation.dto.request.ApplyStatusRequest;
import com.team.cklob.gami.domain.mentoring.repository.ApplyRepository;
import com.team.cklob.gami.domain.mentoring.service.MentoringApplyStatusService;
import com.team.cklob.gami.global.event.CreateChatRoomEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentoringApplyStatusServiceImpl implements MentoringApplyStatusService {

    private final ApplyRepository applyRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void execute(ApplyStatusRequest request, Long applyId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(ApplyNotFoundException::new);

        apply.updateApplyStatus(request.applyStatus());

        if (apply.getApplyStatus() == ApplyStatus.ACCEPTED) {
            eventPublisher.publishEvent(new CreateChatRoomEvent(apply.getMentee().getId(), apply.getMentor().getId(), applyId));
        }
    }
}
