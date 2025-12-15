package com.team.cklob.gami.domain.admin.service.impl;

import com.team.cklob.gami.domain.admin.dto.request.AdminReportProcessRequest;
import com.team.cklob.gami.domain.admin.dto.response.AdminReportDetailResponse;
import com.team.cklob.gami.domain.admin.service.AdminReportProcessService;
import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.exception.InvalidReportRequestException;
import com.team.cklob.gami.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminReportProcessServiceImpl implements AdminReportProcessService {

    private final ReportRepository reportRepository;

    @Override
    public AdminReportDetailResponse processReport(Long reportId, AdminReportProcessRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(InvalidReportRequestException::new);

        if (report.isProcessed()) {
            throw new InvalidReportRequestException();
        }

        report.process(
                request.getReportResult(),
                request.getReportAction(),
                LocalDateTime.now()
        );

        return AdminReportDetailResponse.from(report);
    }
}
