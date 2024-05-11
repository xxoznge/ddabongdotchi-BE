package com.ddabong.ddabongdotchiBE.domain.card.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CardRepositoryImpl implements CardRepository {

	private final CardJpaRepository cardJpaRepository;

	@Override
	public Optional<Card> findById(Long Id) {
		return cardJpaRepository.findById(Id);
	}

	@Override
	public Card save(Card card) {
		return cardJpaRepository.save(card);
	}
}
