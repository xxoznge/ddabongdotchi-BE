package com.ddabong.ddabongdotchiBE.domain.report.dto.request;

import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;
import com.ddabong.ddabongdotchiBE.domain.report.entity.ReportReason;

public record ReportCreateRequest(
	String target,
	ReportReason reportReason
) {

	public Report toEntity() {
		return Report.builder()
			.reportReason(reportReason)
			.target(target)
			.build();
	}
}
