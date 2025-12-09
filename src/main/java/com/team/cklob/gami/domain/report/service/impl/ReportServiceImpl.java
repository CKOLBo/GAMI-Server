package com.team.cklob.gami.domain.report.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.report.dto.request.ReportCreateRequest;
import com.team.cklob.gami.domain.report.dto.response.ReportResponse;
import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.entity.constant.ReportAction;
import com.team.cklob.gami.domain.report.entity.constant.ReportResult;
import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import com.team.cklob.gami.domain.report.exception.AlreadyReportedException;
import com.team.cklob.gami.domain.report.exception.NotFoundReportTargetException;
import com.team.cklob.gami.domain.report.exception.InvalidReportRequestException;
import com.team.cklob.gami.domain.report.repository.ReportRepository;
import com.team.cklob.gami.domain.report.service.ReportService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final MemberUtil memberUtil;

    @Override
    public ReportResponse createReport(ReportCreateRequest request) {
        Member reporter = memberUtil.getCurrentMember();

        if (request.getPostId() == null || request.getReason() == null || request.getReason().isBlank()) {
            throw new InvalidReportRequestException();
        }

        if (reportRepository.existsByReporterIdAndPostId(reporter.getId(), request.getPostId())) {
            throw new AlreadyReportedException();
        }

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(NotFoundReportTargetException::new);

        Member reportedMember = post.getMember();

        Report report = Report.builder()
                .reporter(reporter)
                .reportedMember(reportedMember)
                .post(post)
                .reason(request.getReason())
                .reportType(ReportType.ETC)
                .result(ReportResult.PENDING)
                .action(ReportAction.NONE)
                .reportAt(LocalDateTime.now())
                .build();

        Report saved = reportRepository.save(report);

        return new ReportResponse(saved.getId());
    }
}

