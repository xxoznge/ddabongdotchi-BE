package com.ddabong.ddabongdotchiBE.domain.card.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;

public record CardCreateResponse(
	String username,
	Long id,
	String title,
	String mood,
	String content,
	String type
) {

	public static CardCreateResponse from(Card card) {
		return new CardCreateResponse(
			card.getUser().getUsername(),
			card.getId(),
			card.getTitle(),
			card.getMood(),
			card.getContent(),
			card.getType().name()
		);
	}
}
