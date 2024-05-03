package com.ddabong.ddabongdotchiBE.domain.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.security.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.security.dto.JoinUserRequest;
import com.ddabong.ddabongdotchiBE.domain.security.dto.JoinUserResponse;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.exception.SecurityCustomException;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.exception.TokenErrorCode;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.security.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
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

	private final JwtUtil jwtUtil;

	@PostMapping("/join")
	public ApiResponse<JoinUserResponse> join(@Valid @RequestBody JoinUserRequest request) {
		return ApiResponse.onSuccess(userService.join(request));
	}

	@GetMapping("/reissue")
	public ApiResponse<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		try {
			jwtUtil.validateRefreshToken(refreshToken);
			return ApiResponse.onSuccess(
				jwtUtil.reissueToken(refreshToken)
			);
		} catch (ExpiredJwtException eje) {
			throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED, eje);
		} catch (IllegalArgumentException iae) {
			throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN, iae);
		}
	}

	@GetMapping("/test")
	public ApiResponse<String> register(@UserResolver User user) {
		return ApiResponse.onSuccess(user.getUsername());
	}
}
