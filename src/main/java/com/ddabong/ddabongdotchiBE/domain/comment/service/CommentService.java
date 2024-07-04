package com.ddabong.ddabongdotchiBE.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardErrorCode;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.card.repository.CardRepository;
import com.ddabong.ddabongdotchiBE.domain.comment.dto.request.CommentCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.comment.dto.response.CommentCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.comment.entity.Comment;
import com.ddabong.ddabongdotchiBE.domain.comment.repository.CommentRepository;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

	private final CardRepository cardRepository;
	private final CommentRepository commentRepository;

	public CommentCreateResponse createComment(
		User authUser,
		CommentCreateRequest request
	) {

		Card card = cardRepository.findById(request.cardId())
			.orElseThrow(() -> new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND));

		final Comment comment = commentRepository.save(request.toEntity(authUser, card));
		card.increaseCount();
		return CommentCreateResponse.from(comment);
	}
}
