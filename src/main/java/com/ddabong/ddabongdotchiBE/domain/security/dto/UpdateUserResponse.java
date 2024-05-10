package com.ddabong.ddabongdotchiBE.domain.security.dto;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.Builder;

@Builder
public record UpdateUserResponse(
	String username,
	String nickname,
	String description
) {
	public static UpdateUserResponse from(User user) {
		return UpdateUserResponse.builder()
			.username(user.getUsername())
			.nickname(user.getNickname())
			.description(user.getDescription())
			.build();
	}
}
