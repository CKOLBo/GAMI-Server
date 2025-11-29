package com.team.cklob.gami.global.security.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class InvalidTokenException extends GlobalException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
