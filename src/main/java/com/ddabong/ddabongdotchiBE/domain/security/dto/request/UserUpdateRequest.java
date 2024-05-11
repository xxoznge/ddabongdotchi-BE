package com.ddabong.ddabongdotchiBE.domain.security.dto.request;

public record UserUpdateRequest(
	String nickname,
	String description
) {
}
