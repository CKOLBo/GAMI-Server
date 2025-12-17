package com.team.cklob.gami.domain.auth.controller;

import com.team.cklob.gami.domain.auth.dto.request.*;
import com.team.cklob.gami.domain.auth.dto.response.TokenResponse;
import com.team.cklob.gami.domain.auth.service.*;
import com.team.cklob.gami.domain.auth.exception.TooManyRequestsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원 인증 / 인가 API")
public class AuthController {

    private final SendCodeService sendCodeService;
    private final VerifyCodeService verifyCodeService;
    private final SignUpService signUpService;
    private final SignInService signInService;
    private final ReissueTokenService reissueTokenService;
    private final ChangePasswordService changePasswordService;
    private final SignOutService signOutService;

    @Operation(
            summary = "이메일 인증 코드 발송",
            description = "회원가입을 위한 이메일 인증 코드를 발송한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 코드 발송 성공"),
            @ApiResponse(responseCode = "429", description = "요청 횟수 초과", content = @Content)
    })
    @PostMapping("/email/send-code")
    public ResponseEntity<Void> sendCode(
            @RequestBody SendCodeRequest request
    ) throws TooManyRequestsException {
        sendCodeService.execute(request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "이메일 인증 코드 검증",
            description = "이메일로 받은 인증 코드를 검증한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 코드 검증 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 인증 코드", content = @Content),
            @ApiResponse(responseCode = "429", description = "요청 횟수 초과", content = @Content)
    })
    @PostMapping("/email/verification-code")
    public ResponseEntity<Void> verifyCode(
            @RequestBody VerifyCodeRequest request
    ) throws TooManyRequestsException {
        verifyCodeService.execute(request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "회원가입",
            description = "이메일 인증이 완료된 사용자를 회원으로 등록한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 오류", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody @Valid SignUpRequest request
    ) {
        signUpService.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인하고 JWT 토큰을 발급한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(
            @RequestBody @Valid SignInRequest request
    ) {
        TokenResponse response = signInService.execute(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "토큰 재발급",
            description = "Refresh Token으로 Access Token을 재발급한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "토큰 재발급 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content)
    })
    @PatchMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
            @Parameter(description = "Refresh Token", required = true)
            @RequestHeader("RefreshToken") String refreshToken
    ) {
        TokenResponse tokenResponse = reissueTokenService.execute(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "로그인된 사용자의 비밀번호를 변경한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        changePasswordService.execute(request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "로그아웃",
            description = "현재 로그인된 사용자를 로그아웃 처리한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/signout")
    public ResponseEntity<Void> signOut(
            HttpServletRequest request
    ) {
        String accessToken = request.getHeader("Authorization").substring(7);
        signOutService.execute(accessToken);
        return ResponseEntity.noContent().build();
    }
}
