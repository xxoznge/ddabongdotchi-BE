package com.ddabong.ddabongdotchiBE.domain.user.dto.response;

import com.ddabong.ddabongdotchiBE.domain.user.jwt.dto.JwtDto;

import lombok.Builder;

@Builder
public record ReissueResponse(
	String accessToken,
	String refreshToken
) {

	public static ReissueResponse from(JwtDto jwtDto) {
		return ReissueResponse.builder()
			.accessToken(jwtDto.accessToken())
			.refreshToken(jwtDto.refreshToken())
			.build();
	}
}
