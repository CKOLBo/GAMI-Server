package com.team.cklob.gami.global.security.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class InternalServerErrorException extends GlobalException {
    public InternalServerErrorException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
