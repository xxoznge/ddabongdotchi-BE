package com.ddabong.ddabongdotchiBE.domain.card.repository;

import java.util.Optional;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;

public interface CardRepository {
	Optional<Card> findById(Long Id);

	Card save(Card card);
}
