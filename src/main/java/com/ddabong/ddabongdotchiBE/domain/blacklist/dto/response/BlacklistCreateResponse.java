package com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;
import com.ddabong.ddabongdotchiBE.domain.report.entity.ReportReason;

import lombok.Builder;

@Builder
public record BlacklistCreateResponse(

	Long id,
	String username,
	String target,
	ReportReason reportReason
) {

	public static BlacklistCreateResponse from(Blacklist blacklist) {
		return BlacklistCreateResponse.builder()
			.id(blacklist.getId())
			.username(blacklist.getUser().getUsername())
			.target(blacklist.getTarget().getUsername())
			.build();
	}
}
