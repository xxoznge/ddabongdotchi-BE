package com.ddabong.ddabongdotchiBE.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddabong.ddabongdotchiBE.domain.comment.entity.Comment;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByCardIdAndUser_UserStatusOrderByCreatedAtDesc(Long cardId, UserStatus userStatus);
}
