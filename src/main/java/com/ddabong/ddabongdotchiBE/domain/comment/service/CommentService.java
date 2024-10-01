package com.ddabong.ddabongdotchiBE.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.blacklist.repository.BlacklistRepository;
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
	private final BlacklistRepository blacklistRepository;

	/* 차단된 사용자 ID 리스트 가져오기 */
	private List<Long> getBlockedUser(User user) {
		return blacklistRepository.findByUser(user).stream()
			.map(blacklist -> blacklist.getTarget().getId())
			.toList();
	}

	public CommentCreateResponse createComment(
		User user,
		CommentCreateRequest request
	) {
		Card card = cardRepository.findById(request.cardId())
			.orElseThrow(() -> new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND));

		List<Long> blockedUser = getBlockedUser(user);
		if (blockedUser.contains(card.getUser().getId())) {
			throw new CardExceptionHandler(CardErrorCode.UNAUTHORIZED_ACCESS);
		}

		final Comment comment = commentRepository.save(request.toEntity(user, card));
		card.increaseCount();
		return CommentCreateResponse.from(comment);
	}
}
