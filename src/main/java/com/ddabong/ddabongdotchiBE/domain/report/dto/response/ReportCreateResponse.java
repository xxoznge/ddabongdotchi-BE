package com.ddabong.ddabongdotchiBE.domain.report.dto.response;

import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;
import com.ddabong.ddabongdotchiBE.domain.report.entity.ReportReason;

import lombok.Builder;

@Builder
public record ReportCreateResponse(

	Long id,
	String target,
	ReportReason reportReason
) {

	public static ReportCreateResponse from(Report report) {
		return ReportCreateResponse.builder()
			.id(report.getId())
			.target(report.getTarget())
			.reportReason(report.getReportReason())
			.build();
	}
}
