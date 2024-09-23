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

	// 전체 카드 최신순
	List<Card> findAllByOrderByCreatedAtDesc();

	// 전체 카드 인기순
	List<Card> findAllByOrderByCommentCountDescCreatedAtDesc();

	// 타입 별 카드 최신순
	List<Card> findAllByTypeOrderByCreatedAtDesc(FortuneType type);

	// 타입 별 카드 인기순
	List<Card> findAllByTypeOrderByCommentCountDescCreatedAtDesc(FortuneType type);

	// 오늘의 따봉도치 랭킹
	@Query("SELECT c FROM Card c WHERE c.createdAt >= :today ORDER BY c.commentCount DESC LIMIT 3")
	List<Card> findTop3CommentedCardsToday(@Param("today") LocalDateTime today);

	// 카드 없을 시 오늘의 따봉도치 랜덤 조회
	@Query("SELECT c FROM Card c ORDER BY RAND() LIMIT 3")
	List<Card> findRandom3Cards();

	// 타입 별 카드 마지막 업로드 시간 조회
	@Query("SELECT c.lastUploadTime FROM Card c WHERE c.type = :fortuneType ORDER BY c.lastUploadTime DESC")
	LocalDateTime findLastUploadTimeByType(@Param("fortuneType") FortuneType fortuneType);
}
