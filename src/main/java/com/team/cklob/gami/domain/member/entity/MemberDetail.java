package com.team.cklob.gami.domain.member.entity;

import com.team.cklob.gami.domain.member.entity.constant.Gender;
import com.team.cklob.gami.domain.member.entity.constant.Major;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_detail")
public class MemberDetail {

    @Id
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "major", nullable = false)
    @Enumerated(EnumType.STRING)
    private Major major;

    @Column(name = "generation",  nullable = false, length = 10)
    private Integer generation;

    @Builder
    public MemberDetail(Member member, Gender gender, Major major, Integer generation) {
        this.member = member;
        this.gender = gender;
        this.major = major;
        this.generation = generation;
    }

    public void updateMajor(Major major) {
        this.major = major;
    }
}
