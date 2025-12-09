package com.team.cklob.gami.domain.report.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class InvalidReportRequestException extends GlobalException {
    public InvalidReportRequestException() {
        super(ErrorCode.INVALID_REPORT_REQUEST);
    }
}