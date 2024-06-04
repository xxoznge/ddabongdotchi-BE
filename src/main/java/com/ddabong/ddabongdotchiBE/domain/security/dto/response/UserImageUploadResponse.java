package com.ddabong.ddabongdotchiBE.domain.security.dto.response;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.Builder;

@Builder
public record UserImageUploadResponse(
	String imageUrl
) {

	public static UserImageUploadResponse from(User user) {
		return UserImageUploadResponse.builder()
			.imageUrl(user.getImageUrl())
			.build();
	}
}
