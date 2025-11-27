package com.team.cklob.gami.domain.member.entity;

import com.team.cklob.gami.domain.member.entity.constant.MemberRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email",  unique = true,  nullable = false,  length = 30)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "role",  nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
