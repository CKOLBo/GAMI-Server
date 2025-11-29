package com.team.cklob.gami.domain.post.entity;

import com.team.cklob.gami.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    public static Post create(Member member, String title, String content) {
        LocalDateTime now = LocalDateTime.now();
        return Post.builder()
                .member(member)
                .title(title)
                .content(content)
                .createdAt(now)
                .updateAt(now)
                .likeCount(0)
                .build();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updateAt = LocalDateTime.now();
    }

    public void incrementLikeCount() {
        this.likeCount += 1;
    }

    public void decrementLikeCount() {
        this.likeCount -= 1;
    }
}