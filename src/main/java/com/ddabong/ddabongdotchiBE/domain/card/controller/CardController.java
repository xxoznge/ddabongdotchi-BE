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

	@PostMapping(value = "", consumes = "multipart/form-data")
	public ApiResponse<CardCreateResponse> createCard(
		@UserResolver User authUser,
		@RequestPart(value = "request") @Valid CardCreateRequest request,
		@RequestPart(name = "cardImage") MultipartFile file
	) {
		return ApiResponse.onSuccess(cardService.createCard(authUser, request, file));
	}

	@GetMapping("/{cardId}")
	public ApiResponse<CardDetailGetResponse> getCardDetail(
		@PathVariable Long cardId
	) {
		return ApiResponse.onSuccess(cardQueryService.getCardDetail(cardId));
	}

	@GetMapping("/top")
	public ApiResponse<List<CardSummaryGetResponse>> getTopCardToday() {
		return ApiResponse.onSuccess(cardQueryService.getTopCardToday());
	}

	@GetMapping("")
	public ApiResponse<List<CardSummaryGetResponse>> getCard(
		@RequestParam(name = "sort") CardStatus sortStatus
	) {
		if (sortStatus == CardStatus.RECENT) {
			return ApiResponse.onSuccess(cardQueryService.getRecentCard());
		}

		if (sortStatus == CardStatus.POPULAR) {
			return ApiResponse.onSuccess(cardQueryService.getPopularCard());
		}
		return ApiResponse.onSuccess(Collections.emptyList());
	}

	@GetMapping("/type")
	public ApiResponse<List<CardSummaryGetResponse>> getTypeCard(
		@RequestParam(name = "type") FortuneType type,
		@RequestParam(name = "sort") CardStatus sortStatus
	) {
		if (sortStatus == CardStatus.RECENT) {
			return ApiResponse.onSuccess(cardQueryService.getRecentTypeCard(type));
		}

		if (sortStatus == CardStatus.POPULAR) {
			return ApiResponse.onSuccess(cardQueryService.getPopularTypeCard(type));
		}
		return ApiResponse.onSuccess(Collections.emptyList());
	}

	@DeleteMapping("/{cardId}")
	public ApiResponse<String> deleteCard(
		@UserResolver User authUser,
		@PathVariable Long cardId) {
		cardService.deleteCard(authUser, cardId);
		return ApiResponse.onSuccess("삭제 성공");
	}
}
