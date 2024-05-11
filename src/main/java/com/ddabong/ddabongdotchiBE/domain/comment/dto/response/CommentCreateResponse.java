package com.ddabong.ddabongdotchiBE.domain.comment.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.comment.entity.Comment;

import lombok.Builder;

@Builder
public record CommentCreateResponse(
	Long id,
	String nickname,
	String cardWriter,
	FortuneType type
) {

	public static CommentCreateResponse from(Comment comment) {
		return CommentCreateResponse.builder()
			.id(comment.getId())
			.nickname(comment.getUser().getNickname())
			.cardWriter(comment.getCard().getUser().getNickname())
			.type(comment.getCard().getType())
			.build();
	}
}

