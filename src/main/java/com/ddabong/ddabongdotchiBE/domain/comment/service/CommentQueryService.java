package com.ddabong.ddabongdotchiBE.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.comment.dto.response.CommentGetResponse;
import com.ddabong.ddabongdotchiBE.domain.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentQueryService {

	private final CommentRepository commentRepository;

	public List<CommentGetResponse> getComment(Long cardId) {
		return commentRepository.findByCardIdOrderByCreatedAtDesc(cardId)
			.stream()
			.map(comment -> CommentGetResponse.from(comment))
			.toList();
	}
}
