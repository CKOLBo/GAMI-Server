package com.team.cklob.gami.domain.admin.exception;

import com.team.cklob.gami.global.exception.ErrorCode;

public class AdminReportAlreadyProcessedException extends AdminException {

    public AdminReportAlreadyProcessedException() {
        super(ErrorCode.ADMIN_REPORT_ALREADY_PROCESSED);
    }
}
