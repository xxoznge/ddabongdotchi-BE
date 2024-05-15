package com.ddabong.ddabongdotchiBE.domain.card.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;

import io.lettuce.core.dynamic.annotation.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findById(Long Id);

	List<Card> findAllByOrderByCreatedAtDesc();

	List<Card> findAllByOrderByCommentCountDescCreatedAtDesc();

	List<Card> findAllByTypeOrderByCreatedAtDesc(FortuneType type);

	List<Card> findAllByTypeOrderByCommentCountDescCreatedAtDesc(FortuneType type);

	@Query("SELECT c FROM Card c WHERE c.createdAt >= :today ORDER BY c.commentCount DESC LIMIT 3")
	List<Card> findTop3CommentedCardsToday(@Param("today") LocalDateTime today);

}
