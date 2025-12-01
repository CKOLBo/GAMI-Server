package com.team.cklob.gami.domain.auth.presentation.dto.request;

public record SignInRequest(
   String email,
   String password
) {}
