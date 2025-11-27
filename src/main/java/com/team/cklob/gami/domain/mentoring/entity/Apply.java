package com.team.cklob.gami.domain.mentoring.entity;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Builder
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id",  nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Apply_status", nullable = false)
    private ApplyStatus applyStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Member mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    private Member mentee;

    @CreatedDate
    @Column(name = "created_at",  nullable = false)
    private LocalDateTime createdAt;
}
