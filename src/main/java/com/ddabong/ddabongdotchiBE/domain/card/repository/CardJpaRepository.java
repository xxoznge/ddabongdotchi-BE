package com.ddabong.ddabongdotchiBE.domain.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;

public interface CardJpaRepository extends JpaRepository<Card, Long> {
}
