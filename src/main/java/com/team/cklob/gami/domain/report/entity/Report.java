package com.team.cklob.gami.domain.report.entity;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.report.entity.constant.ReportAction;
import com.team.cklob.gami.domain.report.entity.constant.ReportResult;
import com.team.cklob.gami.domain.report.entity.constant.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporterd_member_id", nullable = false)
    private Member reportedMember;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType  reportType;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_result", nullable = false)
    private ReportResult result;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_action",  nullable = false)
    private ReportAction action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "reason",  nullable = false,  length = 255)
    private String reason;

    @Column(name = "report_at", nullable = false)
    private LocalDateTime reportAt;
}
