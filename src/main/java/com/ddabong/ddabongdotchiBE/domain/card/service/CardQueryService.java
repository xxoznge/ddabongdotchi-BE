package com.ddabong.ddabongdotchiBE.domain.card.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardErrorCode;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.card.repository.CardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CardQueryService {

	private final CardRepository cardRepository;

	public CardDetailGetResponse getCardDetail(Long cardId) {
		final Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND));

		return CardDetailGetResponse.from(card);
	}
}
