package com.ddabong.ddabongdotchiBE.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.blacklist.repository.BlacklistRepository;
import com.ddabong.ddabongdotchiBE.domain.comment.dto.response.CommentGetResponse;
import com.ddabong.ddabongdotchiBE.domain.comment.repository.CommentRepository;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.enums.UserStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentQueryService {

	private final CommentRepository commentRepository;
	private final BlacklistRepository blacklistRepository;

	/* 차단된 사용자 ID 리스트 가져오기 */
	private List<Long> getBlockedUser(User user) {
		return blacklistRepository.findByUser(user).stream()
			.map(blacklist -> blacklist.getTarget().getId())
			.toList();
	}

	public List<CommentGetResponse> getComment(User user, Long cardId) {
		List<Long> blockedUser = getBlockedUser(user);
		return commentRepository.findByCardIdAndUser_UserStatusOrderByCreatedAtDesc(cardId, UserStatus.ACTIVE)
			.stream()
			.filter(card -> !blockedUser.contains(card.getUser().getId()))
			.map(CommentGetResponse::from)
			.toList();
	}
}
