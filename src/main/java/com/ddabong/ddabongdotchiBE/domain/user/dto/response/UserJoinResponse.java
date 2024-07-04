package com.ddabong.ddabongdotchiBE.domain.user.dto.response;

import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import lombok.Builder;

@Builder
public record UserJoinResponse(
	Long id,
	String username,
	String nickname,
	String description,
	String imageUrl
) {

	public static UserJoinResponse from(User user) {
		return UserJoinResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.description(user.getDescription())
			.imageUrl(user.getImageUrl())
			.build();
	}
}
