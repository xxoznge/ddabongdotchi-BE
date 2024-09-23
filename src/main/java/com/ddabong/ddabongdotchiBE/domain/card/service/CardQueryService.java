package com.ddabong.ddabongdotchiBE.domain.card.service;

import java.time.Duration;
import java.time.LocalDateTime;
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

	/* 카드 상세 조회 */
	public CardDetailGetResponse getCardDetail(Long cardId) {
		final Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND));

		return CardDetailGetResponse.from(card);
	}

	/* 오늘의 따봉도치 랭킹 조회 */
	public List<CardSummaryGetResponse> getTopCardToday() {
		LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
		List<Card> top3CommentedCards = cardRepository.findTop3CommentedCardsToday(today);

		// 댓글 많은 카드가 없을 경우 바로 랜덤 3개 카드 반환
		return top3CommentedCards.isEmpty()
			? cardRepository.findRandom3Cards().stream()
			.map(CardSummaryGetResponse::from)
			.toList()
			: top3CommentedCards.stream()
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 전체 카드 최신순 조회 */
	public List<CardSummaryGetResponse> getRecentCard() {
		return cardRepository.findAllByOrderByCreatedAtDesc()
			.stream()
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 전체 카드 인기순 조회 */
	public List<CardSummaryGetResponse> getPopularCard() {
		return cardRepository.findAllByOrderByCommentCountDescCreatedAtDesc()
			.stream()
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 타입 별 카드 최신순 조회 */
	public List<CardSummaryGetResponse> getRecentTypeCard(FortuneType fortuneType) {
		return cardRepository.findAllByTypeOrderByCreatedAtDesc(fortuneType)
			.stream()
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 타입 별 카드 인기순 조회 */
	public List<CardSummaryGetResponse> getPopularTypeCard(FortuneType fortuneType) {
		return cardRepository.findAllByTypeOrderByCommentCountDescCreatedAtDesc(fortuneType)
			.stream()
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 타입 별 카드 마지막 업로드 시간 조회 */
	public String getLastUploadTime(FortuneType type) {
		LocalDateTime lastUploadTime = cardRepository.findLastUploadTimeByType(type);
		if (lastUploadTime == null) {
			return "마지막 업로드 시간이 없습니다.";
		}
		Duration duration = Duration.between(lastUploadTime, LocalDateTime.now());
		long minutes = duration.toMinutes();
		return minutes + "분 전";
	}
}
