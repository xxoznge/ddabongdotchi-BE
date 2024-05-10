package com.ddabong.ddabongdotchiBE.domain.security.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.security.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.security.dto.JoinUserRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.JoinUserResponse;
import com.ddabong.ddabongdotchiBE.domain.security.dto.UpdatePasswordRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.UpdateUserRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.UpdateUserResponse;
import com.ddabong.ddabongdotchiBE.domain.security.dto.UserDetailGetResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.security.service.UserQueryService;
import com.ddabong.ddabongdotchiBE.domain.security.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import umc.springumc.security.jwt.dto.JwtDto;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

	private final UserService userService;
	private final UserQueryService userQueryService;
	private final JwtUtil jwtUtil;

	@PostMapping("/join")
	public ApiResponse<JoinUserResponse> join(@Valid @RequestBody JoinUserRequest request) {
		return ApiResponse.onSuccess(userService.join(request));
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
		userService.deactivate(user.getUsername());
		return ApiResponse.onSuccess("삭제 성공");
	}

	@PatchMapping(value = "/password")
	public ApiResponse<String> updatePassword(
		@UserResolver User user,
		@RequestBody @Valid UpdatePasswordRequest request
	) {
		userService.updatePassword(user.getUsername(), request);
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
	public ApiResponse<UserDetailGetResponse> getMyUser(@UserResolver User user) {
		return ApiResponse.onSuccess(UserDetailGetResponse.from(user));
	}

	@PatchMapping(value = "/me")
	public ApiResponse<UpdateUserResponse> updateMyUser(
		@UserResolver User user,
		@RequestBody @Valid UpdateUserRequest request) {
		return ApiResponse.onSuccess(userService.updateMyUser(user.getUsername(), request));
	}

}
