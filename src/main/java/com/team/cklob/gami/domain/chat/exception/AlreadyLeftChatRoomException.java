package com.team.cklob.gami.domain.chat.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class AlreadyLeftChatRoomException extends GlobalException {
    public AlreadyLeftChatRoomException() {
        super(ErrorCode.ALREADY_LEFT_CHAT_ROOM);
    }
}
