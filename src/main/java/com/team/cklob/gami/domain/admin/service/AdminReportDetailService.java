package com.team.cklob.gami.domain.admin.service;

import com.team.cklob.gami.domain.admin.dto.response.AdminReportDetailResponse;

public interface AdminReportDetailService {

    AdminReportDetailResponse getReport(Long reportId);
}
