package com.team.cklob.gami.domain.postlike.entity;

import com.team.cklob.gami.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "post_like",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_post_like",
                columnNames = {"member_email", "post_id"}
        )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id", nullable = false)
    private Long id;

    @Column(name = "member_email", nullable = false, length = 255)
    private String memberEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
