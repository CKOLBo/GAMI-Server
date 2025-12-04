package com.team.cklob.gami.domain.mentoring.service;

import com.team.cklob.gami.domain.mentoring.presentation.dto.response.GetSentApplyResponse;

import java.util.List;

public interface GetSentApplyService {
    List<GetSentApplyResponse> execute();
}
