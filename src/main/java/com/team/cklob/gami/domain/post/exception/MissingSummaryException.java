package com.team.cklob.gami.domain.post.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class MissingSummaryException extends GlobalException {
    public MissingSummaryException() {
        super(ErrorCode.MISSING_SUMMARY);
    }
}
