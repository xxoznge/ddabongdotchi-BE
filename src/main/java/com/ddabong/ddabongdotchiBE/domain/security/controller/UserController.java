package com.ddabong.ddabongdotchiBE.domain.security.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.security.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.PasswordUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.UserJoinRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.request.UserUpdateRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.MyCardGetResponse;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.UserDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.UserJoinResponse;
import com.ddabong.ddabongdotchiBE.domain.security.dto.response.UserUpdateResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.dto.JwtDto;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.security.service.UserQueryService;
import com.ddabong.ddabongdotchiBE.domain.security.service.UserService;

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
	private final JwtUtil jwtUtil;

	@PostMapping(value = "/join", consumes = "multipart/form-data")
	public ApiResponse<UserJoinResponse> join(
		@RequestPart(value = "request") @Valid UserJoinRequest request,
		@RequestPart(name = "profileImage") MultipartFile file
	) {
		return ApiResponse.onSuccess(userService.join(request, file));
	}

	@GetMapping("/reissue")
	public ApiResponse<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		return ApiResponse.onSuccess(jwtUtil.reissueToken(refreshToken));
	}

	@GetMapping("/test")
	public ApiResponse<String> test(@UserResolver User user) {
		return ApiResponse.onSuccess(user.getUsername());
	}

	@DeleteMapping("/me")
	public ApiResponse<String> deleteUser(@UserResolver User user) {
		userService.deactivate(user);
		return ApiResponse.onSuccess("삭제 성공");
	}

	@PatchMapping(value = "/password")
	public ApiResponse<String> updatePassword(
		@UserResolver User user,
		@RequestBody @Valid PasswordUpdateRequest request
	) {
		userService.updatePassword(user, request);
		return ApiResponse.onSuccess("비밀번호 변경 성공");
	}

	@GetMapping("/username")
	public ApiResponse<Boolean> checkUsername(@RequestParam String username) {
		return ApiResponse.onSuccess(userQueryService.checkUsername(username));
	}

	@GetMapping("/nickname")
	public ApiResponse<Boolean> checkNickname(@RequestParam String nickname) {
		return ApiResponse.onSuccess(userQueryService.checkNickname(nickname));
	}

	@GetMapping("/me")
	public ApiResponse<UserDetailGetResponse> getMyUser(@UserResolver User authUser) {
		return ApiResponse.onSuccess(UserDetailGetResponse.from(authUser));
	}

	@PatchMapping(value = "/me", consumes = "multipart/form-data")
	public ApiResponse<UserUpdateResponse> updateMyUser(
		@UserResolver User user,
		@RequestPart @Valid UserUpdateRequest request,
		@RequestPart(value = "profileImage") MultipartFile file) {
		return ApiResponse.onSuccess(userService.updateMyUser(user, request, file));
	}

	@GetMapping("/me/card")
	public ApiResponse<List<MyCardGetResponse>> getMyCards(@UserResolver User user) {
		return ApiResponse.onSuccess(UserQueryService.getMyCard(user));
	}

	@GetMapping("/health")
	public String healthCheck() {
		return "OK";
	}
}
