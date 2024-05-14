package com.ddabong.ddabongdotchiBE.domain.card.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.card.dto.request.CardCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardSummaryGetResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.CardStatus;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.card.service.CardQueryService;
import com.ddabong.ddabongdotchiBE.domain.card.service.CardService;
import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.security.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

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

	@PostMapping()
	public ApiResponse<CardCreateResponse> createCard(
		@UserResolver User authUser,
		@RequestBody @Valid CardCreateRequest request
	) {
		return ApiResponse.onSuccess(cardService.createCard(authUser, request));
	}

	@GetMapping("/{cardId}")
	public ApiResponse<CardDetailGetResponse> getCardDetail(
		@PathVariable Long cardId
	) {
		return ApiResponse.onSuccess(cardQueryService.getCardDetail(cardId));
	}

	@DeleteMapping("/{cardId}")
	public ApiResponse<String> deleteCard(
		@UserResolver User authUser,
		@PathVariable Long cardId) {
		cardService.deleteCard(authUser, cardId);
		return ApiResponse.onSuccess("삭제 성공");
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
}
