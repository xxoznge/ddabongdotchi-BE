package com.ddabong.ddabongdotchiBE.domain.security.dto.response;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.Builder;

@Builder
public record UserUpdateResponse(
	String username,
	String nickname,
	String description,
	String imageUrl
) {
	public static UserUpdateResponse from(User user) {
		return UserUpdateResponse.builder()
			.username(user.getUsername())
			.nickname(user.getNickname())
			.description(user.getDescription())
			.imageUrl(user.getImageUrl())
			.build();
	}
}
