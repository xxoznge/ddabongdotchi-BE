package com.ddabong.ddabongdotchiBE.domain.security.dto;

public record UpdateUserRequest(
	String nickname,
	String description
) {
}
