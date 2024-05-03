package com.ddabong.ddabongdotchiBE.domain.security.dto;

import com.ddabong.ddabongdotchiBE.domain.security.entity.RoleType;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JoinUserRequest(
	@NotBlank(message = "[ERROR] 아이디 입력은 필수 입니다.")
	String username,
	@NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
	@Size(min = 10, message = "[ERROR] 비밀번호는 최소 10자리 이이어야 합니다.")
	String password,
	@NotBlank(message = "[ERROR] 닉네임 입력은 필수 입니다.")
	String nickname,
	@NotBlank(message = "[ERROR] 소개글 입력은 필수 입니다.")
	String description
) {
	public User toEntity(String encodedPassword) {
		return User.builder()
			.username(username)
			.password(encodedPassword)
			.nickname(nickname)
			.description(description)
			.roleType(RoleType.USER)
			.build();
	}
}
