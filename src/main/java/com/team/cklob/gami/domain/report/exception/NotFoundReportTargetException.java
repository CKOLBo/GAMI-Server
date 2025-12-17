package com.team.cklob.gami.domain.report.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundReportTargetException extends GlobalException {
    public NotFoundReportTargetException() {
        super(ErrorCode.NOT_FOUND_REPORT_TARGET);
    }
}
