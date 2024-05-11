package com.ddabong.ddabongdotchiBE.domain.security.dto.response;

import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

public record UserDetailGetResponse(
	String username,
	String nickname,
	String description
) {
	public static UserDetailGetResponse from(User user) {
		return new UserDetailGetResponse(
			user.getUsername(),
			user.getNickname(),
			user.getDescription()
		);
	}
}
