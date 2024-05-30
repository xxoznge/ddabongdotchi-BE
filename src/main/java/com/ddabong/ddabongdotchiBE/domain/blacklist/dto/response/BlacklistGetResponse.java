package com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;

import lombok.Builder;

@Builder
public record BlacklistGetResponse(
	Long id,
	String username,
	String target,
	String targetNickname
) {

	public static BlacklistGetResponse from(Blacklist blacklist) {
		return BlacklistGetResponse.builder()
			.id(blacklist.getId())
			.username(blacklist.getUser().getUsername())
			.target(blacklist.getTarget().getUsername())
			.targetNickname(blacklist.getTarget().getNickname())
			.build();
	}
}
