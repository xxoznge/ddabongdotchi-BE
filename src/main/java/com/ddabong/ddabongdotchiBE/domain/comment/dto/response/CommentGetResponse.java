package com.ddabong.ddabongdotchiBE.domain.comment.dto.response;

import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.comment.entity.Comment;

import lombok.Builder;

@Builder
public record CommentGetResponse(
	Long id,
	String nickname,
	String cardWriter,
	FortuneType type
) {

	public static CommentGetResponse from(Comment comment) {
		return CommentGetResponse.builder()
			.id(comment.getId())
			.nickname(comment.getUser().getNickname())
			.cardWriter(comment.getCard().getUser().getNickname())
			.type(comment.getCard().getType())
			.build();
	}
}
