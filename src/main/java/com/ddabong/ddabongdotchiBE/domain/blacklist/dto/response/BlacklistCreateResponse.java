package com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;

import lombok.Builder;

@Builder
public record BlacklistCreateResponse(

	Long id,
	String username,
	Long targetId,
	String target

) {

	public static BlacklistCreateResponse from(Blacklist blacklist) {
		return BlacklistCreateResponse.builder()
			.id(blacklist.getId())
			.username(blacklist.getUser().getUsername())
			.targetId(blacklist.getTarget().getId())
			.target(blacklist.getTarget().getUsername())
			.build();
	}
}
