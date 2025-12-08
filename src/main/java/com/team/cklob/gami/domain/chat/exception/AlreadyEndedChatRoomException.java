package com.team.cklob.gami.domain.chat.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class AlreadyEndedChatRoomException extends GlobalException {
    public AlreadyEndedChatRoomException() {
        super(ErrorCode.ALREADY_EXIST_CHAT_ROOM);
    }
}
