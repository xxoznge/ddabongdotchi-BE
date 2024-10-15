package com.ddabong.ddabongdotchiBE.domain.card.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.card.dto.request.CardCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardErrorCode;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.card.repository.CardRepository;
import com.ddabong.ddabongdotchiBE.domain.s3.S3Service;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CardService {

	private final CardRepository cardRepository;
	private final S3Service s3Service;

	/* 카드 작성 */
	public CardCreateResponse createCard(
		User authUser,
		CardCreateRequest request
	) {
		final Card card = cardRepository.save(request.toEntity(authUser));
		cardRepository.save(card);
		return CardCreateResponse.from(card);
	}

	/* 카드 삭제 */
	public void deleteCard(User user, Long cardId) {
		final Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND));
		if (!card.getUser().getUsername().equals(user.getUsername())) {
			throw new CardExceptionHandler(CardErrorCode.UNAUTHORIZED_ACCESS);
		}
		cardRepository.delete(card);
	}
}
