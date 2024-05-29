package com.ddabong.ddabongdotchiBE.domain.report.dto.response;

import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;
import com.ddabong.ddabongdotchiBE.domain.report.entity.ReportReason;

import lombok.Builder;

@Builder
public record ReportCreateResponse(

	Long id,
	String username,
	String target,
	ReportReason reportReason
) {

	public static ReportCreateResponse from(Report report) {
		return ReportCreateResponse.builder()
			.id(report.getId())
			.username(report.getUser().getUsername())
			.target(report.getTarget().getUsername())
			.reportReason(report.getReportReason())
			.build();
	}
}
