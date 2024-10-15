package com.ddabong.ddabongdotchiBE.domain.user.dto.request;

public record UserUpdateRequest(
	String nickname,
	String description,
	String imageUrl
) {
}
