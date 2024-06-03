package com.ddabong.ddabongdotchiBE.domain.security.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
