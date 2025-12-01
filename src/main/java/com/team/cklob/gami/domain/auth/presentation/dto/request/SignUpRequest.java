package com.team.cklob.gami.domain.auth.presentation.dto.request;

import com.team.cklob.gami.domain.member.entity.constant.Gender;
import com.team.cklob.gami.domain.member.entity.constant.Major;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        String password,

        @NotBlank
        String name,

        @NotNull
        Integer generation,

        @NotNull
        Gender gender,

        @NotNull
        Major major
) {}
