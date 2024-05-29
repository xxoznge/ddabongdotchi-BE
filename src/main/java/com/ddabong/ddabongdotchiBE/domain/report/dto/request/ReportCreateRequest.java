package com.ddabong.ddabongdotchiBE.domain.report.dto.request;

import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;
import com.ddabong.ddabongdotchiBE.domain.report.entity.ReportReason;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import jakarta.validation.constraints.NotNull;

public record ReportCreateRequest(
	@NotNull
	String target,
	ReportReason reportReason
) {

	public Report toEntity(User user, User target) {
		return Report.builder()
			.user(user)
			.target(target)
			.reportReason(reportReason)
			.build();
	}
}
