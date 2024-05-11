package com.ddabong.ddabongdotchiBE.domain.security.dto;

public record UserUpdateRequest(
	String nickname,
	String description
) {
}
