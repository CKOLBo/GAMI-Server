package com.team.cklob.gami.domain.admin.dto.response;

import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.entity.constant.ReportAction;
import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminReportDetailResponse {

    private final Long id;
    private final ReportType reportType;
    private final Long targetId;
    private final String reason;
    private final ReportAction reportAction;
    private final String memberRole;
    private final LocalDateTime processedAt;

    public static AdminReportDetailResponse from(Report report) {
        return AdminReportDetailResponse.builder()
                .id(report.getId())
                .reportType(report.getReportType())
                .targetId(report.getPost().getId())
                .reason(report.getReason())
                .reportAction(report.getAction())
                .memberRole(report.getReportedMember().getRole().name())
                .processedAt(report.getProcessedAt())
                .build();
    }
}
