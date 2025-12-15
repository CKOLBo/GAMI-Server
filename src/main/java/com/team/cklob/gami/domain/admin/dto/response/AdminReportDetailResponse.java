package com.team.cklob.gami.domain.admin.dto.response;

import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.entity.constant.ReportAction;
import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminReportDetailResponse {

    private Long id;
    private ReportType reportType;
    private Long targetId;
    private String reason;
    private ReportAction reportAction;
    private String memberRole;
    private LocalDateTime processedAt;

    public static AdminReportDetailResponse from(Report report) {
        AdminReportDetailResponse response = new AdminReportDetailResponse();
        response.id = report.getId();
        response.reportType = report.getReportType();
        response.targetId = report.getPost().getId();
        response.reason = report.getReason();
        response.reportAction = report.getAction();
        response.memberRole = report.getReportedMember().getRole().name();
        response.processedAt = report.getProcessedAt();
        return response;
    }
}
