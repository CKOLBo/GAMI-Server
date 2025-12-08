package com.team.cklob.gami.domain.chat.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class CannotSendMessageToEndedRoomException extends GlobalException {
    public CannotSendMessageToEndedRoomException() {
        super(ErrorCode.CANNOT_SEND_MESSAGE_TO_ENDED_ROOM);
    }
}
