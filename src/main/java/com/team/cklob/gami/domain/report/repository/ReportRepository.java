package com.team.cklob.gami.domain.report.repository;

import com.team.cklob.gami.domain.report.entity.Report;
import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByReporterIdAndPostIdAndReportType(
            Long reporterId,
            Long postId,
            ReportType reportType
    );

    @Query("""
        SELECT r
        FROM Report r
        JOIN FETCH r.reporter
        JOIN FETCH r.post
    """)
    List<Report> findAllWithReporterAndPost();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Report r where r.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
