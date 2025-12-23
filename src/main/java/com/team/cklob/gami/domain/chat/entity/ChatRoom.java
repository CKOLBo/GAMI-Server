package com.team.cklob.gami.domain.chat.entity;

import com.team.cklob.gami.domain.chat.entity.constant.RoomStatus;
import com.team.cklob.gami.domain.chat.exception.NotChatRoomMemberException;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.mentoring.entity.Apply;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
@EntityListeners(AuditingEntityListener.class)
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
    @JoinColumn(name = "apply_Id",  nullable = false)
    private Apply apply;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status",  nullable = false)
    private RoomStatus roomStatus;

    @Column(name = "last_message", nullable = false)
    private String lastMessage;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "mentor_left")
    @Builder.Default
    private boolean mentorLeft = false;

    @Column(name = "mentee_left")
    @Builder.Default
    private boolean menteeLeft = false;

    public void updateRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public boolean isActive() {
        return this.roomStatus == RoomStatus.ACTIVE && this.endedAt == null;
    }

    public void end() {
        this.roomStatus = RoomStatus.ENDED;
        this.endedAt = LocalDateTime.now();
    }

    public void leave(Member member) {
        if (member.getId().equals(mentor.getId())) {
            this.mentorLeft = true;
        } else if (member.getId().equals(mentee.getId())) {
            this.menteeLeft = true;
        } else {
            throw new NotChatRoomMemberException();
        }

        if (mentorLeft && menteeLeft) {
            this.end();
        }
    }

    public boolean hasLeft(Member member) {
        if (member.getId().equals(mentor.getId())) {
            return mentorLeft;
        } else if (member.getId().equals(mentee.getId())) {
            return menteeLeft;
        }
        return false;
    }

    public boolean isMember(Member member) {
        return member.getId().equals(mentor.getId())
                || member.getId().equals(mentee.getId());
    }

    public void updateLastMessage(String message) {
        this.lastMessage = message;
    }
}
