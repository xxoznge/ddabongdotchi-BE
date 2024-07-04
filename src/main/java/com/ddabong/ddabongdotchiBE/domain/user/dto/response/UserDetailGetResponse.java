package com.ddabong.ddabongdotchiBE.domain.user.dto.response;

import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import lombok.Builder;

@Builder
public record UserDetailGetResponse(
	Long id,
	String username,
	String nickname,
	String description,
	String imageUrl
) {

	public static UserDetailGetResponse from(User user) {
		return UserDetailGetResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.description(user.getDescription())
			.imageUrl(user.getImageUrl())
			.build();
	}
}
