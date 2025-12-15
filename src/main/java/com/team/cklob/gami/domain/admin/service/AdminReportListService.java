package com.team.cklob.gami.domain.admin.service;

import com.team.cklob.gami.domain.admin.dto.response.AdminReportResponse;

import java.util.List;

public interface AdminReportListService {

    List<AdminReportResponse> getReports();
}
