package com.team.cklob.gami.domain.report.dto.request;

import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateRequest {

    @NotNull
    private Long postId;

    @NotNull
    private ReportType reportType;

    @NotBlank
    private String reason;

    private String reasonDetail;
}
