package com.ddabong.ddabongdotchiBE.domain.card.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.blacklist.repository.BlacklistRepository;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardSummaryGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardErrorCode;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.card.repository.CardRepository;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CardQueryService {

	private final CardRepository cardRepository;
	private final BlacklistRepository blacklistRepository;

	/* 차단된 사용자 ID 리스트 가져오기 */
	private List<Long> getBlockedUser(User user) {
		return blacklistRepository.findByUser(user).stream()
			.map(blacklist -> blacklist.getTarget().getId())
			.toList();
	}

	/* 오늘의 따봉도치 랭킹 조회 */
	public List<CardSummaryGetResponse> getTopCardToday(User user) {
		LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
		List<Card> top3CommentedCards = cardRepository.findTop3CommentedCardsToday(today, UserStatus.ACTIVE);

		List<Long> blockedUser = getBlockedUser(user);

		// 댓글 많은 카드가 없을 경우 랜덤 3개 카드 반환
		if (top3CommentedCards.isEmpty()) {
			return cardRepository.findRandom3Cards(UserStatus.ACTIVE, today).stream()
				.filter(card -> !blockedUser.contains(card.getUser().getId()))
				.map(CardSummaryGetResponse::from)
				.toList();
		}

		// 오늘 카드가 3개 미만일 경우 과거 카드에서 추가로 가져오기
		if (top3CommentedCards.size() < 3) {
			int needed = 3 - top3CommentedCards.size();
			List<Card> pastCards = cardRepository.findRandomPastCards(UserStatus.ACTIVE);

			// 추가 카드 필터링
			List<Card> additionalCards = pastCards.stream()
				.filter(card -> !blockedUser.contains(card.getUser().getId()))
				.limit(needed)
				.toList();

			// 기존 카드와 추가 카드 합치기 후 반환
			return Stream.concat(top3CommentedCards.stream().map(CardSummaryGetResponse::from),
					additionalCards.stream().map(CardSummaryGetResponse::from))
				.toList();
		}

		// 댓글 많은 카드가 3개인 경우
		return top3CommentedCards.stream()
			.filter(card -> !blockedUser.contains(card.getUser().getId()))
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 카드 상세 조회 */
	public CardDetailGetResponse getCardDetail(User user, Long cardId) {
		final Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND));

		// 카드 소유자 확인
		if (card.getUser().getUserStatus() == UserStatus.INACTIVE) {
			throw new CardExceptionHandler(CardErrorCode.UNAUTHORIZED_ACCESS); // 탈퇴한 사용자일 경우 예외 처리
		}

		List<Long> blockedUser = getBlockedUser(user);

		// 카드 소유자가 차단된 사용자 목록에 포함되지 않은 경우에만 반환
		if (blockedUser.contains(card.getUser().getId())) {
			throw new CardExceptionHandler(CardErrorCode.UNAUTHORIZED_ACCESS); // 차단된 사용자일 경우 예외 처리
		}

		return CardDetailGetResponse.from(card);
	}

	/* 전체 카드 최신순 조회 */
	public List<CardSummaryGetResponse> getRecentCard(User user) {
		List<Long> blockedUser = getBlockedUser(user);
		return cardRepository.findByUser_UserStatusOrderByCreatedAtDesc(UserStatus.ACTIVE)
			.stream()
			.filter(card -> !blockedUser.contains(card.getUser().getId()))
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 전체 카드 인기순 조회 */
	public List<CardSummaryGetResponse> getPopularCard(User user) {
		List<Long> blockedUser = getBlockedUser(user);
		return cardRepository.findByUser_UserStatusOrderByCommentCountDescCreatedAtDesc(UserStatus.ACTIVE)
			.stream()
			.filter(card -> !blockedUser.contains(card.getUser().getId()))
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 타입 별 카드 최신순 조회 */
	public List<CardSummaryGetResponse> getRecentTypeCard(User user, FortuneType fortuneType) {
		List<Long> blockedUser = getBlockedUser(user);
		return cardRepository.findByUser_UserStatusAndTypeOrderByCreatedAtDesc(UserStatus.ACTIVE, fortuneType)
			.stream()
			.filter(card -> !blockedUser.contains(card.getUser().getId()))
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 타입 별 카드 인기순 조회 */
	public List<CardSummaryGetResponse> getPopularTypeCard(User user, FortuneType fortuneType) {
		List<Long> blockedUser = getBlockedUser(user);
		return cardRepository.findByUser_UserStatusAndTypeOrderByCommentCountDescCreatedAtDesc(
				UserStatus.ACTIVE, fortuneType)
			.stream()
			.filter(card -> !blockedUser.contains(card.getUser().getId()))
			.map(CardSummaryGetResponse::from)
			.toList();
	}

	/* 타입 별 카드 마지막 업로드 시간 조회 */
	public String getLastUploadTime(FortuneType type) {
		LocalDateTime lastUploadTime = cardRepository.findLastUploadTimeByType(type, UserStatus.ACTIVE);

		if (lastUploadTime == null) {
			return "마지막 업로드 시간이 없습니다.";
		}

		Duration duration = Duration.between(lastUploadTime, LocalDateTime.now());
		long minutes = duration.toMinutes();
		long hours = duration.toHours();
		long days = duration.toDays();

		if (minutes < 60) {
			return minutes + "분 전";
		} else if (hours < 24) {
			return hours + "시간 전";
		} else {
			return days + "일 전";
		}
	}
}
