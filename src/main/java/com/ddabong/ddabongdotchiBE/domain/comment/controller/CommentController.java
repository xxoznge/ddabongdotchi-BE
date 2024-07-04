package com.ddabong.ddabongdotchiBE.domain.comment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.comment.dto.request.CommentCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.comment.dto.response.CommentCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.comment.dto.response.CommentGetResponse;
import com.ddabong.ddabongdotchiBE.domain.comment.service.CommentQueryService;
import com.ddabong.ddabongdotchiBE.domain.comment.service.CommentService;
import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.user.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {

	private final CommentService commentService;
	private final CommentQueryService commentQueryService;

	@PostMapping()
	public ApiResponse<CommentCreateResponse> createComment(
		@UserResolver User authUser,
		@RequestBody @Valid CommentCreateRequest request
	) {
		return ApiResponse.onSuccess(commentService.createComment(authUser, request));
	}

	@GetMapping("/{cardId}")
	public ApiResponse<List<CommentGetResponse>> getComment(@PathVariable Long cardId) {
		return ApiResponse.onSuccess(commentQueryService.getComment(cardId));
	}
}
