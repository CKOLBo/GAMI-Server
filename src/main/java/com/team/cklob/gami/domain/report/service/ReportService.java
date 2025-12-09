package com.team.cklob.gami.domain.report.service;

import com.team.cklob.gami.domain.report.dto.request.ReportCreateRequest;
import com.team.cklob.gami.domain.report.dto.response.ReportResponse;

public interface ReportService {
    ReportResponse createReport(ReportCreateRequest request);
}
