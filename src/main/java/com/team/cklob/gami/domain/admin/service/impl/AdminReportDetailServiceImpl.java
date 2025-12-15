package com.team.cklob.gami.domain.admin.service.impl;

import com.team.cklob.gami.domain.admin.dto.response.AdminReportDetailResponse;
import com.team.cklob.gami.domain.admin.service.AdminReportDetailService;
import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.exception.InvalidReportRequestException;
import com.team.cklob.gami.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportDetailServiceImpl implements AdminReportDetailService {

    private final ReportRepository reportRepository;

    @Override
    public AdminReportDetailResponse getReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(InvalidReportRequestException::new);

        return AdminReportDetailResponse.from(report);
    }
}
