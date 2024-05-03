package com.ddabong.ddabongdotchiBE.domain.auth.dto;

public record LoginUserRequest(
	String username,
	String password
) {
}
