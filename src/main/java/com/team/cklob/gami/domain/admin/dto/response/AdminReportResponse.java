package com.team.cklob.gami.domain.admin.dto.response;

import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.entity.constant.ReportResult;
import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminReportResponse {

    private final Long id;
    private final ReportType reportType;
    private final ReportResult reportResult;
    private final String reporterName;
    private final Long reportedPostId;

    public static AdminReportResponse from(Report report) {
        return AdminReportResponse.builder()
                .id(report.getId())
                .reportType(report.getReportType())
                .reportResult(report.getResult())
                .reporterName(report.getReporter().getName())
                .reportedPostId(report.getPost().getId())
                .build();
    }
}
