package com.team.cklob.gami.domain.auth.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class TooManyRequestsException extends GlobalException {
    public TooManyRequestsException() {
        super(ErrorCode.TOO_MANY_REQUESTS);
    }
}
