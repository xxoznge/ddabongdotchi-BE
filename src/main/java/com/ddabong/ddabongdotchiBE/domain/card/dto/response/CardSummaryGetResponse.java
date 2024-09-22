package com.ddabong.ddabongdotchiBE.domain.card.dto.response;

import java.time.LocalDateTime;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;

import lombok.Builder;

@Builder
public record CardSummaryGetResponse(
	Long id,
	String nickname,
	String userImageUrl,
	String title,
	FortuneType type,
	Long commentCount,
	LocalDateTime createdAt,
	String imageUrl
) {

	public static CardSummaryGetResponse from(Card card) {
		return CardSummaryGetResponse.builder()
			.id(card.getId())
			.nickname(card.getUser().getNickname())
			.userImageUrl(card.getUser().getImageUrl())
			.title(card.getTitle())
			.type(card.getType())
			.commentCount(card.getCommentCount())
			.createdAt(card.getCreatedAt())
			.imageUrl(card.getImageUrl())
			.build();
	}
}
