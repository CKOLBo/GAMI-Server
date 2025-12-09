package com.team.cklob.gami.domain.report.exception;

import com.team.cklob.gami.global.exception.GlobalException;
import com.team.cklob.gami.global.exception.ErrorCode;

public class AlreadyReportedException extends GlobalException {
    public AlreadyReportedException() {
        super(ErrorCode.ALREADY_REPORTED);
    }
}
