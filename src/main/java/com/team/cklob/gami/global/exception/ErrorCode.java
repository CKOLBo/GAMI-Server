package com.team.cklob.gami.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // AUTH
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    UNVERIFIED_EMAIL(400, "인증되지 않은 이메일입니다."),
    NOT_MATCHED_CODE(400, "인증 코드가 일치하지 않습니다"),
    NOT_FOUND_USER(401, "존재하지 않는 사용자입니다."),
    UNAUTHORIZED(401, "이메일 또는 비밀번호가 잘못되었습니다."),
    NOT_FOUND_VERIFY_CODE(404, "만료되었거나 존재하지 않는 인증 코드입니다."),
    TOO_MANY_REQUESTS(429, "너무 빠르게 요청하고 있습니다. 잠시 후 다시 시도해주세요."),
    EMAIL_ALREADY_EXISTS(409, "이미 등록된 이메일입니다"),

    // COMMON
    INTERNAL_SERVER_ERROR(500, "예기치 못한 서버 에러가 발생했습니다."),

    // MEMBER
    NOT_MATCHED_PASSWORD(400, "비밀번호가 일치하지 않습니다"),
    INVALID_MEMBER_PRINCIPAL(401, "현재 인증된 사용자의 정보가 유효하지 않습니다."),
    NOT_FOUND_MEMBER_DETAIL(404, "회원 상세 정보를 찾을 수 없습니다."),
    NOT_FOUND_MEMBER(404, "해당 회원을 찾을 수 없습니다."),
  
    // POST
    INVALID_POST_REQUEST(400, "게시글 요청 정보가 유효하지 않습니다."),
    NOT_FOUND_POST(404, "해당 게시글을 찾을 수 없습니다."),
    NOT_FOUND_POST_PAGE(404, "요청한 페이지가 존재하지 않습니다."),
    FORBIDDEN_POST_ACCESS(403, "게시글에 대한 권한이 없습니다."),

    // REPORT
    INVALID_REPORT_REQUEST(400, "신고 요청 정보가 유효하지 않습니다."),
    ALREADY_REPORTED(409, "이미 신고한 대상입니다."),
    NOT_FOUND_REPORT_TARGET(404, "신고 대상이 존재하지 않습니다."),

    // COMMENT
    INVALID_COMMENT_REQUEST(400, "댓글 요청 정보가 유효하지 않습니다."),
    NOT_FOUND_COMMENT(404, "해당 댓글을 찾을 수 없습니다."),
    FORBIDDEN_COMMENT_ACCESS(403, "댓글에 대한 권한이 없습니다."),
    INVALID_POST_IMAGE_EXTENSION(400, "지원하지 않는 게시글 이미지 확장자입니다."),
    POST_IMAGE_UPLOAD_FAILED(500, "게시글 이미지 업로드에 실패했습니다."),
    POST_IMAGE_DELETE_FAILED(500, "게시글 이미지 삭제에 실패했습니다."),

    // MENTORING
    RANDOM_MENTOR_NOT_FOUND(404, "조건에 맞는 멘토를 찾을 수 없습니다."),
    ALREADY_REGISTERED_MENTOR(409, "이미 등록된 멘토입니다"),
    APPLY_NOT_FOUND(404, "존재하지 않는 신청입니다."),
    SELF_APPLY_NOT_ALLOWED(400, "자기 자신한테 신청을 보낼 수 없습니다."),

    // CHAT
    ALREADY_EXIST_CHAT_ROOM(400, "이미 생성된 채팅방입니다."),
    ALREADY_LEFT_CHAT_ROOM(409, "이미 나간 채팅방입니다."),
    NOT_CHAT_ROOM_MEMBER(403, "채팅방 멤버가 아닙니다."),
    CANNOT_SEND_MESSAGE_TO_ENDED_ROOM(400, "종료된 채팅방에는 메시지를 보낼 수 없습니다."),
    CANNOT_SEND_MESSAGE_AFTER_LEAVING(400, "나간 채팅방에는 메시지를 보낼 수 없습니다."),
    ALREADY_EXIST_ACTIVE_CHAT_ROOM(409, "이미 활성화된 채팅방이 존재합니다."),
    NOT_FOUND_CHAT_MEMBER(404, "이 채팅방에 소속되지 않았습니다."),
    NOT_FOUND_CHAT_ROOM(404, "존재하지 않는 채팅방입니다."),
    ALREADY_ENDED_CHAT_ROOM(409, "이미 종료된 채팅방입니다.");

    private final int status;
    private final String message;
}
