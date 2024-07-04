package com.ddabong.ddabongdotchiBE.domain.user.dto.request;

public record UserLoginRequest(
	String username,
	String password
) {
}
