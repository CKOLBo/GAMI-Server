package com.team.cklob.gami.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),

    NOT_FOUND_MEMBER_DETAIL(404, "회원 상세 정보를 찾을 수 없습니다.");


    private  final int code;
    private final String message;
}
