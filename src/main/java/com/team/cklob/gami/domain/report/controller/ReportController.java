package com.team.cklob.gami.domain.report.controller;

import com.team.cklob.gami.domain.report.dto.request.ReportCreateRequest;
import com.team.cklob.gami.domain.report.dto.response.ReportResponse;
import com.team.cklob.gami.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> create(@RequestBody ReportCreateRequest request) {
        ReportResponse response = reportService.createReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
