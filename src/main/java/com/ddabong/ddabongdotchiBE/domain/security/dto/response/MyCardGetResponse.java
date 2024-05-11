package com.ddabong.ddabongdotchiBE.domain.security.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;

import lombok.Builder;

@Builder
public record MyCardGetResponse(
	String username,
	Long cardId,
	String title,
	FortuneType type

) {

	public static MyCardGetResponse from(Card card) {
		return MyCardGetResponse.builder()
			.username(card.getUser().getUsername())
			.cardId(card.getId())
			.title(card.getTitle())
			.type(card.getType())
			.build();
	}
}
