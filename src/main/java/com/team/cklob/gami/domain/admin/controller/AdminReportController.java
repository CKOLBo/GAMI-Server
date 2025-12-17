package com.team.cklob.gami.domain.admin.controller;

import com.team.cklob.gami.domain.admin.dto.request.AdminReportProcessRequest;
import com.team.cklob.gami.domain.admin.dto.response.AdminReportDetailResponse;
import com.team.cklob.gami.domain.admin.dto.response.AdminReportResponse;
import com.team.cklob.gami.domain.admin.service.AdminReportDetailService;
import com.team.cklob.gami.domain.admin.service.AdminReportListService;
import com.team.cklob.gami.domain.admin.service.AdminReportProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/report")
@PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
@Tag(name = "Admin - Report", description = "관리자 신고 관리 API")
@SecurityRequirement(name = "Bearer")
public class AdminReportController {

    private final AdminReportListService adminReportListService;
    private final AdminReportDetailService adminReportDetailService;
    private final AdminReportProcessService adminReportProcessService;

    @Operation(
            summary = "신고 목록 조회",
            description = "관리자가 접수된 신고 목록을 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "신고 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = AdminReportResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "관리자 권한 없음", content = @Content)
    })
    @GetMapping
    public List<AdminReportResponse> getReports() {
        return adminReportListService.getReports();
    }

    @Operation(
            summary = "신고 상세 조회",
            description = "신고 ID로 신고 상세 정보를 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "신고 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = AdminReportDetailResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "관리자 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "신고를 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{reportId}")
    public AdminReportDetailResponse getReport(
            @Parameter(description = "신고 ID", example = "1")
            @PathVariable Long reportId
    ) {
        return adminReportDetailService.getReport(reportId);
    }

    @Operation(
            summary = "신고 처리",
            description = "신고를 처리하고 처리 결과를 저장한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "신고 처리 성공",
                    content = @Content(schema = @Schema(implementation = AdminReportDetailResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "관리자 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "신고를 찾을 수 없음", content = @Content)
    })
    @PostMapping("/{reportId}")
    public AdminReportDetailResponse processReport(
            @Parameter(description = "신고 ID", example = "1")
            @PathVariable Long reportId,
            @RequestBody AdminReportProcessRequest request
    ) {
        return adminReportProcessService.processReport(reportId, request);
    }
}
