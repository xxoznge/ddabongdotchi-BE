package com.ddabong.ddabongdotchiBE.domain.comment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.comment.dto.request.CommentCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.comment.dto.response.CommentCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.comment.service.CommentService;
import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.security.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping()
	public ApiResponse<CommentCreateResponse> createComment(
		@UserResolver User authUser,
		@RequestBody @Valid CommentCreateRequest request
	) {
		return ApiResponse.onSuccess(commentService.createComment(authUser, request));
	}
}
