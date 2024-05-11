package com.ddabong.ddabongdotchiBE.domain.card.dto.request;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import jakarta.validation.constraints.NotBlank;

public record CardCreateRequest(
	@NotBlank(message = "[ERROR] 카드 제목 입력은 필수 입니다.")
	String title,

	@NotBlank(message = "[ERROR] 카드 컨디션 입력은 필수 입니다.")
	String mood,

	@NotBlank(message = "[ERROR] 카드 내용 입력은 필수 입니다.")
	String content,

	@NotBlank(message = "[ERROR] 카드 상태 입력은 필수 입니다.")
	String type
) {

	public Card toEntity(
		User cardUser
	) {
		return Card.builder()
			.title(title)
			.mood(mood)
			.content(content)
			.type(FortuneType.valueOf(type))
			.cardUser(cardUser)
			.build();
	}
}
