package com.ddabong.ddabongdotchiBE.domain.user.dto.response;

import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

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
