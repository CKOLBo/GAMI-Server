package com.team.cklob.gami.domain.admin.exception;

import com.team.cklob.gami.global.exception.ErrorCode;

public class AdminReportNotFoundException extends AdminException {

    public AdminReportNotFoundException() {
        super(ErrorCode.ADMIN_REPORT_NOT_FOUND);
    }
}