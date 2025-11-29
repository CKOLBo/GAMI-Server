package com.team.cklob.gami.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),

    INTERNAL_SERVER_ERROR(500, "예기치 못한 서버 에러가 발생했습니다."),

    INVALID_MEMBER_PRINCIPAL(401, "현재 인증된 사용자의 정보가 유효하지 않습니다."),
    NOT_FOUND_MEMBER_DETAIL(404, "회원 상세 정보를 찾을 수 없습니다."),
    NOT_FOUND_MEMBER(404, "해당 회원을 찾을 수 없습니다.");




    private  final int status;
    private final String message;
}
