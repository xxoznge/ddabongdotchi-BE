package com.ddabong.ddabongdotchiBE.domain.security.dto;

public record LoginUserRequest(
	String username,
	String password
) {
}
