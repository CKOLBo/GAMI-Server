package com.team.cklob.gami.domain.chat.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class CannotSendMessageAfterLeavingException extends GlobalException {
    public CannotSendMessageAfterLeavingException() {
        super(ErrorCode.CANNOT_SEND_MESSAGE_AFTER_LEAVING);
    }
}
