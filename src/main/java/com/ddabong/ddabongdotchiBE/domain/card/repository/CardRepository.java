package com.ddabong.ddabongdotchiBE.domain.card.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ddabong.ddabongdotchiBE.domain.card.entity.Card;
import com.ddabong.ddabongdotchiBE.domain.card.entity.FortuneType;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

import io.lettuce.core.dynamic.annotation.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findById(Long Id);

	// 전체 카드 최신순
	List<Card> findByUser_UserStatusOrderByCreatedAtDesc(UserStatus userStatus);

	// 전체 카드 인기순
	List<Card> findByUser_UserStatusOrderByCommentCountDescCreatedAtDesc(UserStatus userStatus);

	// 타입 별 카드 최신순
	List<Card> findByUser_UserStatusAndTypeOrderByCreatedAtDesc(UserStatus userStatus, FortuneType type);

	// 타입 별 카드 인기순
	List<Card> findByUser_UserStatusAndTypeOrderByCommentCountDescCreatedAtDesc(UserStatus userStatus,
		FortuneType type);

	// 오늘의 따봉도치 랭킹
	@Query("SELECT c FROM Card c WHERE c.createdAt >= :today AND c.user.userStatus = :userStatus ORDER BY c.commentCount DESC")
	List<Card> findTop3CommentedCardsToday(@Param("today") LocalDateTime today,
		@Param("userStatus") UserStatus userStatus);

	@Query("SELECT c FROM Card c WHERE c.user.userStatus = :userStatus AND SIZE(c.comments) = 0 AND c.createdAt >= :today ORDER BY c.id")
	List<Card> findRandom3Cards(@Param("userStatus") UserStatus userStatus, @Param("today") LocalDateTime today);

	@Query(value = "SELECT c FROM Card c WHERE c.user.userStatus = :userStatus ORDER BY RAND()")
	List<Card> findRandomPastCards(@Param("userStatus") UserStatus userStatus);
	
	// 타입 별 카드 마지막 업로드 시간 조회
	@Query("SELECT c.createdAt FROM Card c WHERE c.type = :fortuneType AND c.user.userStatus = :userStatus ORDER BY c.createdAt DESC")
	LocalDateTime findLastUploadTimeByType(@Param("fortuneType") FortuneType fortuneType,
		@Param("userStatus") UserStatus userStatus);
}
