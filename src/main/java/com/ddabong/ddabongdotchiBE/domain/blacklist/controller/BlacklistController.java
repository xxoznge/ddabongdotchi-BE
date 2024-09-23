package com.ddabong.ddabongdotchiBE.domain.blacklist.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response.BlacklistCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.blacklist.dto.response.BlacklistGetResponse;
import com.ddabong.ddabongdotchiBE.domain.blacklist.service.BlacklistQueryService;
import com.ddabong.ddabongdotchiBE.domain.blacklist.service.BlacklistService;
import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.user.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/blacklists")
@RestController
public class BlacklistController {

	private final BlacklistService blacklistService;
	private final BlacklistQueryService blacklistQueryService;

	/* 차단하기 */
	@PostMapping("")
	public ApiResponse<BlacklistCreateResponse> createBlacklist(
		@UserResolver User authUser,
		@RequestParam Long targetId
	) {
		return ApiResponse.onSuccess(blacklistService.createBlacklist(authUser, targetId));
	}

	/* 차단 목록 조회 */
	@GetMapping("")
	public ApiResponse<List<BlacklistGetResponse>> getBlacklist(@UserResolver User user) {
		return ApiResponse.onSuccess(blacklistQueryService.getBlacklist(user));
	}

	/* 차단 해제 */
	@DeleteMapping("")
	public ApiResponse<String> deleteBlacklist(
		@UserResolver User user,
		@RequestParam Long targetId
	) {
		blacklistService.deleteBlacklist(user, targetId);
		return ApiResponse.onSuccess("삭제 성공");
	}
}
