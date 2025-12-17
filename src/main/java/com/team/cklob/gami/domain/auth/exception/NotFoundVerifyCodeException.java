package com.team.cklob.gami.domain.auth.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundVerifyCodeException extends GlobalException {
    public NotFoundVerifyCodeException() {
        super(ErrorCode.NOT_FOUND_VERIFY_CODE);
    }
}
