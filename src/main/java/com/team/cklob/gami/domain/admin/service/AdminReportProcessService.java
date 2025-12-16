package com.team.cklob.gami.domain.admin.service;

import com.team.cklob.gami.domain.admin.dto.request.AdminReportProcessRequest;
import com.team.cklob.gami.domain.admin.dto.response.AdminReportDetailResponse;

public interface AdminReportProcessService {

    AdminReportDetailResponse processReport(Long reportId, AdminReportProcessRequest request);
}
