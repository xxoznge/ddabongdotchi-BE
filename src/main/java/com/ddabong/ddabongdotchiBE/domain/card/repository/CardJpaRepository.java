package com.ddabong.ddabongdotchiBE.domain.card.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;

public interface CardJpaRepository extends JpaRepository<Card, Long> {
	Optional<Card> findById(Long Id);
}
