package com.team.cklob.gami.domain.admin.controller;

import com.team.cklob.gami.domain.admin.dto.request.AdminReportProcessRequest;
import com.team.cklob.gami.domain.admin.dto.response.AdminReportDetailResponse;
import com.team.cklob.gami.domain.admin.dto.response.AdminReportResponse;
import com.team.cklob.gami.domain.admin.service.AdminReportDetailService;
import com.team.cklob.gami.domain.admin.service.AdminReportListService;
import com.team.cklob.gami.domain.admin.service.AdminReportProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/report")
@PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
public class AdminReportController {

    private final AdminReportListService adminReportListService;
    private final AdminReportDetailService adminReportDetailService;
    private final AdminReportProcessService adminReportProcessService;

    @GetMapping
    public List<AdminReportResponse> getReports() {
        return adminReportListService.getReports();
    }

    @GetMapping("/{reportId}")
    public AdminReportDetailResponse getReport(@PathVariable Long reportId) {
        return adminReportDetailService.getReport(reportId);
    }

    @PostMapping("/{reportId}")
    public AdminReportDetailResponse processReport(
            @PathVariable Long reportId,
            @RequestBody AdminReportProcessRequest request
    ) {
        return adminReportProcessService.processReport(reportId, request);
    }
}
