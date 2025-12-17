package com.team.cklob.gami.domain.admin.dto.request;

import com.team.cklob.gami.domain.report.entity.constant.ReportAction;
import com.team.cklob.gami.domain.report.entity.constant.ReportResult;
import lombok.Getter;

@Getter
public class AdminReportProcessRequest {

    private ReportResult reportResult;
    private ReportAction reportAction;
}
