package com.team.cklob.gami.domain.member.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotMatchedPasswordException extends GlobalException {
    public NotMatchedPasswordException() {
        super(ErrorCode.NOT_MATCHED_PASSWORD);
    }
}
