package com.team.cklob.gami.domain.report.dto.request;

import lombok.Getter;

@Getter
public class ReportCreateRequest {
    private Long postId;
    private String reason;
    private String reasonDetail;
}
