package com.ddabong.ddabongdotchiBE.domain.comment.dto.request;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.comment.entity.Comment;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

public record CommentCreateRequest(
	Long cardId
) {

	public Comment toEntity(User user, Card card) {
		return Comment.builder()
			.user(user)
			.card(card)
			.build();
	}
}
