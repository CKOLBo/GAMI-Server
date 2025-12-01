package com.team.cklob.gami.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // AUTH
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),

    // COMMON
    INTERNAL_SERVER_ERROR(500, "예기치 못한 서버 에러가 발생했습니다."),

    // MEMBER
    INVALID_MEMBER_PRINCIPAL(401, "현재 인증된 사용자의 정보가 유효하지 않습니다."),
    NOT_FOUND_MEMBER_DETAIL(404, "회원 상세 정보를 찾을 수 없습니다."),
    NOT_FOUND_MEMBER(404, "해당 회원을 찾을 수 없습니다."),

    //auth
    UNVERIFIED_EMAIL(400, "인증되지 않은 이메일입니다."),
    NOT_MATCHED_CODE(400, "인증 코드가 일치하지 않습니다"),
    NOT_FOUND_USER(401, "존재하지 않는 사용자입니다."),
    UNAUTHORIZED(401, "이메일 또는 비밀번호가 잘못되었습니다."),
    NOT_FOUND_VERIFY_CODE(404, "만료되었거나 존재하지 않는 인증 코드입니다."),
    TOO_MANY_REQUESTS(429, "너무 빠르게 요청하고 있습니다. 잠시 후 다시 시도해주세요."),
    EMAIL_ALREADY_EXISTS(409, "이미 등록된 이메일입니다"),
  
    // POST
    INVALID_POST_REQUEST(400, "게시글 요청 정보가 유효하지 않습니다."),
    NOT_FOUND_POST(404, "해당 게시글을 찾을 수 없습니다."),
    NOT_FOUND_POST_PAGE(404, "요청한 페이지가 존재하지 않습니다."),
    FORBIDDEN_POST_ACCESS(403, "게시글에 대한 권한이 없습니다.");

    private final int status;
    private final String message;
}