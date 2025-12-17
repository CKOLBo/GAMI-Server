package com.team.cklob.gami.domain.chat.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotChatRoomMemberException extends GlobalException {
    public NotChatRoomMemberException() {
        super(ErrorCode.NOT_CHAT_ROOM_MEMBER);
    }
}
