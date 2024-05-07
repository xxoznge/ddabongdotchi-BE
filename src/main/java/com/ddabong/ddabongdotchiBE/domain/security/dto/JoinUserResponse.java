package com.ddabong.ddabongdotchiBE.domain.security.dto;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.Builder;

@Builder
public record JoinUserResponse(
	Long id,
	String username,
	String nickname,
	String description
) {

	public static JoinUserResponse from(User user) {
		return JoinUserResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.description(user.getDescription())
			.build();
	}
}