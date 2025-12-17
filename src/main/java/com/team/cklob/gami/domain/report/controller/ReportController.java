package com.team.cklob.gami.domain.report.controller;

import com.team.cklob.gami.domain.report.dto.request.ReportCreateRequest;
import com.team.cklob.gami.domain.report.dto.response.ReportResponse;
import com.team.cklob.gami.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
@Tag(name = "Report", description = "사용자 신고 API")
@SecurityRequirement(name = "Bearer")
public class ReportController {

    private final ReportService reportService;

    @Operation(
            summary = "신고 등록",
            description = "게시글 또는 사용자를 신고한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "신고 등록 성공",
                    content = @Content(schema = @Schema(implementation = ReportResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 신고 요청", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReportResponse> create(
            @RequestBody ReportCreateRequest request
    ) {
        ReportResponse response = reportService.createReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
