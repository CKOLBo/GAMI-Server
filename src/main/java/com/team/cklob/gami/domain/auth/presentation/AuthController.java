package com.team.cklob.gami.domain.auth.presentation;

import com.team.cklob.gami.domain.auth.presentation.dto.request.*;
import com.team.cklob.gami.domain.auth.presentation.dto.responnse.TokenResponse;
import com.team.cklob.gami.domain.auth.service.*;
import com.team.cklob.gami.domain.auth.exception.TooManyRequestsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SendCodeService sendCodeService;
    private final VerifyCodeService verifyCodeService;
    private final SignUpService signUpService;
    private final SignInService signInService;
    private final ReissueTokenService reissueTokenService;
    private final ChangePasswordService changePasswordService;
    private final SignOutService signOutService;

    @PostMapping("/email/send-code")
    public ResponseEntity<Void> sendCode(@RequestBody SendCodeRequest request) throws TooManyRequestsException {
        sendCodeService.execute(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/verification-code")
    public ResponseEntity<Void> verifyCode(@RequestBody VerifyCodeRequest request) throws TooManyRequestsException {
        verifyCodeService.execute(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {
        signUpService.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody @Valid SignInRequest request) {
        TokenResponse response = signInService.execute(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestHeader("RefreshToken") String refreshToken) {
        TokenResponse tokenResponse = reissueTokenService.execute(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.execute(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/signout")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        signOutService.execute(accessToken);
        return ResponseEntity.noContent().build();
    }
}

