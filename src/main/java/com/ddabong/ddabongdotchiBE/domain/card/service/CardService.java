package com.ddabong.ddabongdotchiBE.domain.card.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddabong.ddabongdotchiBE.domain.card.dto.request.CardCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.card.dto.response.CardCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardErrorCode;
import com.ddabong.ddabongdotchiBE.domain.card.exception.CardExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.card.repository.CardRepository;
import com.ddabong.ddabongdotchiBE.domain.s3.util.S3Service;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CardService {

	private final CardRepository cardRepository;
	private final S3Service s3Service;

	public CardCreateResponse createCard(
		User authUser,
		CardCreateRequest request,
		MultipartFile file
	) {
		String imageUrl = s3Service.uploadImage(file);
		final Card card = cardRepository.save(request.toEntity(authUser));
		card.setImageUrl(imageUrl);
		cardRepository.save(card);
		return CardCreateResponse.from(card);
	}

	public void deleteCard(User user, Long cardId) {
		final Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND));
		if (!card.getUser().getUsername().equals(user.getUsername())) {
			throw new CardExceptionHandler(CardErrorCode.CARD_NOT_FOUND);
		}
		cardRepository.delete(card);
	}
}
