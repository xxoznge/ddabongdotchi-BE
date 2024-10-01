package com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;

import lombok.Builder;

@Builder
public record BlacklistGetResponse(
	Long id,
	String username,
	Long targetId,
	String targetUsername,
	String targetNickname,
	String targetImageUrl
) {

	public static BlacklistGetResponse from(Blacklist blacklist) {
		return BlacklistGetResponse.builder()
			.id(blacklist.getId())
			.username(blacklist.getUser().getUsername())
			.targetId(blacklist.getTarget().getId())
			.targetUsername(blacklist.getTarget().getUsername())
			.targetNickname(blacklist.getTarget().getNickname())
			.targetImageUrl(blacklist.getTarget().getImageUrl())
			.build();
	}
}
