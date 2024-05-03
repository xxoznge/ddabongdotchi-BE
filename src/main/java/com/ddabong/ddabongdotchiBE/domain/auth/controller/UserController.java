package com.ddabong.ddabongdotchiBE.domain.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.auth.dto.JoinUserRequest;
import com.ddabong.ddabongdotchiBE.domain.auth.dto.JoinUserResponse;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.dto.JwtDto;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.auth.service.UserService;
import com.ddabong.ddabongdotchiBE.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private final JwtUtil jwtUtil;
	private final UserService userService;

	@PostMapping("/join")
	public ApiResponse<JoinUserResponse> join(@RequestBody JoinUserRequest joinUserRequest) {
		return ApiResponse.onSuccess(userService.join(joinUserRequest));
	}

	@GetMapping("/reissue")
	public ApiResponse<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		return ApiResponse.onSuccess(jwtUtil.reissueToken(refreshToken));
	}

}
