package com.ddabong.ddabongdotchiBE.domain.card.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;

public record CreateCardResponse(
	String username,
	Long id,
	String title,
	String mood,
	String content,
	String type
) {

	public static CreateCardResponse from(Card card) {
		return new CreateCardResponse(
			card.getCardUser().getUsername(),
			card.getId(),
			card.getTitle(),
			card.getMood(),
			card.getContent(),
			card.getType().name()
		);
	}
}
