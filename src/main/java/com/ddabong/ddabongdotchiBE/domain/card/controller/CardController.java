package com.ddabong.ddabongdotchiBE.domain.card.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ddabong.ddabongdotchiBE.domain.card.dto.request.CardCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardSummaryGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.CardStatus;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.card.service.CardQueryService;
import com.ddabong.ddabongdotchiBE.domain.card.service.CardService;
import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.user.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
@RestController
public class CardController {

	private final CardService cardService;
	private final CardQueryService cardQueryService;

	/* 카드 작성 */
	@PostMapping(value = "", consumes = "multipart/form-data")
	public ApiResponse<CardCreateResponse> createCard(
		@UserResolver User authUser,
		@RequestPart(value = "request") @Valid CardCreateRequest request,
		@RequestPart(name = "cardImage") MultipartFile file
	) {
		return ApiResponse.onSuccess(cardService.createCard(authUser, request, file));
	}

	/* 오늘의 따봉도치 랭킹 조회 */
	@GetMapping("/top")
	public ApiResponse<List<CardSummaryGetResponse>> getTopCardToday() {
		return ApiResponse.onSuccess(cardQueryService.getTopCardToday());
	}

	/* 전체 카드 목록 조회 */
	@GetMapping("")
	public ApiResponse<List<CardSummaryGetResponse>> getCard(
		@RequestParam(name = "sort") CardStatus sortStatus
	) {
		// 최신순 조회
		if (sortStatus == CardStatus.RECENT) {
			return ApiResponse.onSuccess(cardQueryService.getRecentCard());
		}
		// 인기순 조회
		if (sortStatus == CardStatus.POPULAR) {
			return ApiResponse.onSuccess(cardQueryService.getPopularCard());
		}
		return ApiResponse.onSuccess(Collections.emptyList());
	}

	/* 테마 별 카드 목록 조회 */
	@GetMapping("/type")
	public ApiResponse<List<CardSummaryGetResponse>> getTypeCard(
		@RequestParam(name = "type") FortuneType type,
		@RequestParam(name = "sort") CardStatus sortStatus
	) {
		// 최신순 조회
		if (sortStatus == CardStatus.RECENT) {
			return ApiResponse.onSuccess(cardQueryService.getRecentTypeCard(type));
		}
		// 인기순 조회
		if (sortStatus == CardStatus.POPULAR) {
			return ApiResponse.onSuccess(cardQueryService.getPopularTypeCard(type));
		}
		return ApiResponse.onSuccess(Collections.emptyList());
	}

	/* 카드 상세 조회 */
	@GetMapping("/{cardId}")
	public ApiResponse<CardDetailGetResponse> getCardDetail(
		@PathVariable Long cardId
	) {
		return ApiResponse.onSuccess(cardQueryService.getCardDetail(cardId));
	}

	/* 타입 별 카드 마지막 업로드 시간 조회 */
	@GetMapping("/last")
	public ApiResponse<String> getLastUploadTime(@RequestParam(name = "type") FortuneType type) {
		String lastUploadTime = cardQueryService.getLastUploadTime(type);
		return ApiResponse.onSuccess(lastUploadTime);
	}

	/* 카드 삭제 */
	@DeleteMapping("/{cardId}")
	public ApiResponse<String> deleteCard(
		@UserResolver User authUser,
		@PathVariable Long cardId) {
		cardService.deleteCard(authUser, cardId);
		return ApiResponse.onSuccess("삭제 성공");
	}
}
