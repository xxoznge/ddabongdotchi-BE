package com.ddabong.ddabongdotchiBE.domain.blacklist.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.request.BlacklistCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response.BlacklistCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response.BlacklistGetResponse;
import com.ddabong.ddabongdotchiBE.domain.blacklist.service.BlacklistQueryService;
import com.ddabong.ddabongdotchiBE.domain.blacklist.service.BlacklistService;
import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.security.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/blacklists")
@RestController
public class BlacklistController {

	private final BlacklistService blacklistService;
	private final BlacklistQueryService blacklistQueryService;

	@PostMapping("")
	public ApiResponse<BlacklistCreateResponse> createBlacklist(
		@UserResolver User authUser,
		@RequestBody BlacklistCreateRequest request
	) {
		return ApiResponse.onSuccess(blacklistService.createBlacklist(authUser, request));
	}

	@GetMapping("")
	public ApiResponse<List<BlacklistGetResponse>> getBlacklist(@UserResolver User user) {
		return ApiResponse.onSuccess(blacklistQueryService.getBlacklist(user));
	}

	@DeleteMapping("/{targetId}")
	public ApiResponse<String> deleteBlacklist(
		@UserResolver User user,
		@PathVariable Long targetId
	) {
		blacklistService.deleteBlacklist(user, targetId);
		return ApiResponse.onSuccess("삭제 성공");
	}
}
