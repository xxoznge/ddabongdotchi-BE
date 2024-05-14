package com.ddabong.ddabongdotchiBE.domain.card.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findById(Long Id);

	List<Card> findAllByOrderByCreatedAtDesc();

	List<Card> findAllByOrderByCommentCountDescCreatedAtDesc();
}
