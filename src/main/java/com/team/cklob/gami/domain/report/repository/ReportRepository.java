package com.team.cklob.gami.domain.report.repository;

import com.team.cklob.gami.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByReporterIdAndPostId(Long reporterId, Long postId);
}
