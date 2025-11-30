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

    // POST
    INVALID_POST_REQUEST(400, "게시글 요청 정보가 유효하지 않습니다."),
    NOT_FOUND_POST(404, "해당 게시글을 찾을 수 없습니다."),
    NOT_FOUND_POST_PAGE(404, "요청한 페이지가 존재하지 않습니다."),
    FORBIDDEN_POST_ACCESS(403, "게시글에 대한 권한이 없습니다.");

    private final int status;
    private final String message;
}