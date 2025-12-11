package com.team.cklob.gami.domain.auth.dto.request;

public record SignInRequest(
   String email,
   String password
) {}
