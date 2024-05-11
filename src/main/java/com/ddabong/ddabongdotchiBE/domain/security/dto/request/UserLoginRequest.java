package com.ddabong.ddabongdotchiBE.domain.security.dto.request;

public record UserLoginRequest(
	String username,
	String password
) {
}
