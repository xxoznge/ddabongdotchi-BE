package com.ddabong.ddabongdotchiBE.domain.security.dto;

public record UserLoginRequest(
	String username,
	String password
) {
}
