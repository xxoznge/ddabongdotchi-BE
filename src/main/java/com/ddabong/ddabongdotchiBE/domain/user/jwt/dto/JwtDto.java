package com.ddabong.ddabongdotchiBE.domain.user.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
