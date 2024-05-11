package com.ddabong.ddabongdotchiBE.domain.card.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;

import lombok.Builder;

@Builder
public record CardDetailGetResponse(
	Long id,
	String username,
	String nickname,
	String title,
	String mood,
	String content,
	FortuneType type
) {

	public static CardDetailGetResponse from(Card card) {
		return CardDetailGetResponse.builder()
			.id(card.getId())
			.username(card.getUser().getUsername())
			.nickname(card.getUser().getNickname())
			.title(card.getTitle())
			.mood(card.getMood())
			.content(card.getContent())
			.type(card.getType())
			.build();
	}
}
