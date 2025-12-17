package com.team.cklob.gami.domain.auth.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotMatchedCodeException extends GlobalException {
    public NotMatchedCodeException() {
        super(ErrorCode.NOT_MATCHED_CODE);
    }
}
