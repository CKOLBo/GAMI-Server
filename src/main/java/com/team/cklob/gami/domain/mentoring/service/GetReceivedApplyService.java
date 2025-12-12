package com.team.cklob.gami.domain.mentoring.service;

import com.team.cklob.gami.domain.mentoring.dto.response.GetReceivedApplyResponse;

import java.util.List;

public interface GetReceivedApplyService {
    List<GetReceivedApplyResponse> execute();
}
