package com.team.cklob.gami.domain.admin.service.impl;

import com.team.cklob.gami.domain.admin.dto.response.AdminReportResponse;
import com.team.cklob.gami.domain.admin.service.AdminReportListService;
import com.team.cklob.gami.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportListServiceImpl implements AdminReportListService {

    private final ReportRepository reportRepository;

    @Override
    public List<AdminReportResponse> getReports() {
        return reportRepository.findAll()
                .stream()
                .map(AdminReportResponse::from)
                .toList();
    }
}
