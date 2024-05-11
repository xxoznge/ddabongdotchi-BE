package com.ddabong.ddabongdotchiBE.domain.card.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.card.dto.request.CreateCardRequest;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CreateCardResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.repository.CardRepository;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CardService {

	private final CardRepository cardRepository;

	public CreateCardResponse createCard(
		User authUser,
		CreateCardRequest request
	) {
		final Card card = cardRepository.save(request.toEntity(authUser));
		return CreateCardResponse.from(card);
	}
}
