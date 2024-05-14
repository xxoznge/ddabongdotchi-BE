package com.ddabong.ddabongdotchiBE.domain.card.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardSummaryGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
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

	public List<CardSummaryGetResponse> getRecentCard() {
		return cardRepository.findAllByOrderByCreatedAtDesc()
			.stream()
			.map(card -> CardSummaryGetResponse.from(card))
			.toList();
	}

	public List<CardSummaryGetResponse> getPopularCard() {
		return cardRepository.findAllByOrderByCommentCountDescCreatedAtDesc()
			.stream()
			.map(card -> CardSummaryGetResponse.from(card))
			.toList();
	}

	public List<CardSummaryGetResponse> getRecentTypeCard(FortuneType fortuneType) {
		return cardRepository.findAllByTypeOrderByCreatedAtDesc(fortuneType)
			.stream()
			.map(card -> CardSummaryGetResponse.from(card))
			.toList();
	}

	public List<CardSummaryGetResponse> getPopularTypeCard(FortuneType fortuneType) {
		return cardRepository.findAllByTypeOrderByCommentCountDescCreatedAtDesc(fortuneType)
			.stream()
			.map(card -> CardSummaryGetResponse.from(card))
			.toList();
	}
}
