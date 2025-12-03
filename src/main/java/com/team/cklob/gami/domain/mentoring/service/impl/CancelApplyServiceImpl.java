package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.exception.ApplyNotFoundException;
import com.team.cklob.gami.domain.mentoring.presentation.dto.request.CancelApplyRequest;
import com.team.cklob.gami.domain.mentoring.repository.ApplyRepository;
import com.team.cklob.gami.domain.mentoring.service.CancelApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelApplyServiceImpl implements CancelApplyService {

    private final ApplyRepository applyRepository;

    @Override
    @Transactional
    public void execute(CancelApplyRequest request, Long applyId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(ApplyNotFoundException::new);

        apply.updateApplyStatus(request.applyStatus());
    }
}
