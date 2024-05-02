package com.ddabong.ddabongdotchiBE.domain.auth.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
