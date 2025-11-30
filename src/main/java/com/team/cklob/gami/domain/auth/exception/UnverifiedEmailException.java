package com.team.cklob.gami.domain.auth.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class UnverifiedEmailException extends GlobalException {
    public UnverifiedEmailException() {
        super(ErrorCode.UNVERIFIED_EMAIL);
    }
}
