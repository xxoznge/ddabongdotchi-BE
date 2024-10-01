package com.ddabong.ddabongdotchiBE.domain.card.dto.request;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import jakarta.validation.constraints.NotBlank;

public record CardCreateRequest(
	@NotBlank(message = "[ERROR] 카드 제목 입력은 필수 입니다.")
	String title,
	String mood,
	String content,
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
			.user(cardUser)
			.build();
	}
}
