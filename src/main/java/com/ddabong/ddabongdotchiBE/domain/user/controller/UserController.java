package com.ddabong.ddabongdotchiBE.domain.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.s3.S3Service;
import com.ddabong.ddabongdotchiBE.domain.user.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.PasswordUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.UserJoinRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.request.UserUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.MyCardGetResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.ReissueResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.UserDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.UserJoinResponse;
import com.ddabong.ddabongdotchiBE.domain.user.dto.response.UserUpdateResponse;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.service.UserQueryService;
import com.ddabong.ddabongdotchiBE.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

	private final UserService userService;
	private final UserQueryService userQueryService;
	private final S3Service s3Service;

	@PostMapping(value = "/join")
	public ApiResponse<UserJoinResponse> join(
		@RequestPart(value = "request") @Valid UserJoinRequest request
	) {
		return ApiResponse.onSuccess(userService.join(request));
	}

	@GetMapping("/username")
	public ApiResponse<Boolean> checkUsername(@RequestParam String username) {
		return ApiResponse.onSuccess(userQueryService.checkUsername(username));
	}

	@GetMapping("/nickname")
	public ApiResponse<Boolean> checkNickname(@RequestParam String nickname) {
		return ApiResponse.onSuccess(userQueryService.checkNickname(nickname));
	}

	@GetMapping("/reissue")
	public ApiResponse<ReissueResponse> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		return ApiResponse.onSuccess(userService.reissueToken(refreshToken));
	}

	@PatchMapping(value = "/password")
	public ApiResponse<String> updatePassword(
		@UserResolver User user,
		@RequestBody @Valid PasswordUpdateRequest request
	) {
		userService.updatePassword(user, request);
		return ApiResponse.onSuccess("비밀번호 변경 성공");
	}

	@GetMapping("/me")
	public ApiResponse<UserDetailGetResponse> getMyUser(@UserResolver User authUser) {
		return ApiResponse.onSuccess(UserDetailGetResponse.from(authUser));
	}

	@GetMapping("/me/card")
	public ApiResponse<List<MyCardGetResponse>> getMyCards(@UserResolver User user) {
		return ApiResponse.onSuccess(UserQueryService.getMyCard(user));
	}

	@PatchMapping(value = "/me")
	public ApiResponse<UserUpdateResponse> updateMyUser(
		@UserResolver User user,
		@RequestPart @Valid UserUpdateRequest request) {
		return ApiResponse.onSuccess(userService.updateMyUser(user, request));
	}

	@DeleteMapping("/me")
	public ApiResponse<String> deleteUser(@UserResolver User user) {
		userService.deactivate(user);
		return ApiResponse.onSuccess("탈퇴 성공");
	}

	@GetMapping("/health")
	public String healthCheck() {
		return "OK";
	}
}
