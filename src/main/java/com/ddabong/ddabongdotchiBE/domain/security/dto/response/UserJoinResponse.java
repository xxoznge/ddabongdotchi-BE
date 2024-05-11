package com.ddabong.ddabongdotchiBE.domain.security.dto.response;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.Builder;

@Builder
public record UserJoinResponse(
	Long id,
	String username,
	String nickname,
	String description
) {

	public static UserJoinResponse from(User user) {
		return UserJoinResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.description(user.getDescription())
			.build();
	}
}
