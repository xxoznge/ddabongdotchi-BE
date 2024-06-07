package com.ddabong.ddabongdotchiBE.domain.card.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;

import lombok.Builder;

@Builder
public record CardCreateResponse(
	Long id,
	String username,
	String title,
	String mood,
	String content,
	FortuneType type,
	String imageUrl
) {

	public static CardCreateResponse from(Card card) {
		return CardCreateResponse.builder()
			.id(card.getId())
			.username(card.getUser().getUsername())
			.title(card.getTitle())
			.mood(card.getMood())
			.content(card.getContent())
			.type(card.getType())
			.imageUrl(card.getImageUrl())
			.build();
	}
}
