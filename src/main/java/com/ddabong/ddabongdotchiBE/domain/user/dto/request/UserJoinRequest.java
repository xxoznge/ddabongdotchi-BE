package com.ddabong.ddabongdotchiBE.domain.user.dto.request;

import com.ddabong.ddabongdotchiBE.domain.user.entity.RoleType;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserJoinRequest(
	@NotBlank(message = "[ERROR] 아이디 입력은 필수 입니다.")
	@Pattern(regexp = "^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ0-9]{3,}$", message = "[ERROR] 아이디는 한글, 영어 또는 숫자로 3글자 이상이어야 합니다.")
	String username,
	@NotBlank(message = "[ERROR] 비밀번호 입력은 필수입니다.")
	@Size(min = 10, max = 100, message = "[ERROR] 비밀번호는 최소 10자리 이상이어야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]{10,100}$", message = "[ERROR] 비밀번호는 영어여야 합니다.")
	String password,
	@NotBlank(message = "[ERROR] 닉네임 입력은 필수입니다.")
	@Pattern(regexp = "^[가-힣ㄱ-ㅎㅏ-ㅣ]{2,7}$", message = "[ERROR] 닉네임은 한글로 2~7글자여야 합니다.")
	String nickname,
	String description,
	String imageUrl
) {

	public User toEntity(String encodedPassword) {
		return User.builder()
			.username(username)
			.password(encodedPassword)
			.nickname(nickname)
			.description(description)
			.imageUrl(imageUrl)
			.roleType(RoleType.USER)
			.userStatus(UserStatus.ACTIVE)
			.build();
	}
}
