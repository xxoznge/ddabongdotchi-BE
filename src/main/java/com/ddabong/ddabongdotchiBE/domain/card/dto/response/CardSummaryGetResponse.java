package com.ddabong.ddabongdotchiBE.domain.card.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;

import lombok.Builder;

@Builder
public record CardSummaryGetResponse(
	Long id,
	String nickname,
	String title,
	FortuneType type
) {

	public static CardSummaryGetResponse from(Card card) {
		return CardSummaryGetResponse.builder()
			.id(card.getId())
			.nickname(card.getUser().getNickname())
			.title(card.getTitle())
			.type(card.getType())
			.build();
	}
}
