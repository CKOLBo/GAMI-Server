package com.team.cklob.gami.domain.admin.dto.response;

import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import lombok.Getter;

@Getter
public class AdminReportResponse {

    private Long id;
    private ReportType reportType;

    public static AdminReportResponse from(Report report) {
        AdminReportResponse response = new AdminReportResponse();
        response.id = report.getId();
        response.reportType = report.getReportType();
        return response;
    }
}
