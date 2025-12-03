package com.team.cklob.gami.domain.chat.entity;

import com.team.cklob.gami.domain.chat.entity.constant.MatchStatus;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.mentoring.entity.Apply;
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
@Table(name = "chat_room")
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id",  nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Member mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    private Member mentee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applyId",  nullable = false)
    private Apply apply;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_status",  nullable = false)
    private MatchStatus matchStatus;

    @Column(name = "last_message", nullable = false)
    private String lastMessage;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
