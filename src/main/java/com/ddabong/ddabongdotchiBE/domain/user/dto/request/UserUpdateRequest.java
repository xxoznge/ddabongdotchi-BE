package com.ddabong.ddabongdotchiBE.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
	@Pattern(regexp = "^[가-힣ㄱ-ㅎㅏ-ㅣ]{2,7}$", message = "[ERROR] 닉네임은 한글로 2~7글자여야 합니다.")
	String nickname,
	String description,
	String imageUrl
) {
}
