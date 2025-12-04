package com.team.cklob.gami.domain.mentoring.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class ApplyNotFoundException extends GlobalException {
    public ApplyNotFoundException() {
        super(ErrorCode.APPLY_NOT_FOUND);
    }
}
