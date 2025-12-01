package com.team.cklob.gami.domain.auth.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundUserException extends GlobalException {
    public NotFoundUserException() {
        super(ErrorCode.NOT_FOUND_USER);
    }
}
