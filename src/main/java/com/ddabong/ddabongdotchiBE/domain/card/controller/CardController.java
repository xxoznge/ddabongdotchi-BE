package com.ddabong.ddabongdotchiBE.domain.card.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.card.dto.request.CreateCardRequest;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CreateCardResponse;
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

	@PostMapping()
	public ApiResponse<CreateCardResponse> createPropose(
		@UserResolver User authUser,
		@RequestBody @Valid CreateCardRequest request
	) {
		return ApiResponse.onSuccess(cardService.createCard(authUser, request));
	}
}
