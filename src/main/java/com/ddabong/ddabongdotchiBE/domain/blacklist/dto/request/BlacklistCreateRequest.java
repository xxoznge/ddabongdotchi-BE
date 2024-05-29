package com.ddabong.ddabongdotchiBE.domain.blacklist.dto.request;

import com.ddabong.ddabongdotchiBE.domain.blacklist.entity.Blacklist;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import jakarta.validation.constraints.NotNull;

public record BlacklistCreateRequest(
	@NotNull
	String target
) {

	public Blacklist toEntity(User user, User target) {
		return Blacklist.builder()
			.user(user)
			.target(target)
			.build();
	}
}
