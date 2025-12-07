package com.team.cklob.gami.domain.chat.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundChatMemberException extends GlobalException {
    public NotFoundChatMemberException() {
        super(ErrorCode.NOT_FOUND_CHAT_MEMBER);
    }
}
