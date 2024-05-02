package com.ddabong.ddabongdotchiBE.domain.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.auth.entity.User;
import com.ddabong.ddabongdotchiBE.domain.auth.exception.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.dto.JwtDto;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.auth.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {

	private UserService userService;

	private final JwtUtil jwtUtil;

	@GetMapping({"/"})
	public String index() {
		return "index";
	}

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}

	// jwt test
	@GetMapping("/loginForm")
	public String login() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String join() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(@RequestBody User user) {
		userService.register(user);
		return "redirect:/loginForm";
	}

	@GetMapping("/reissue")
	public ApiResponse<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		return ApiResponse.onSuccess(jwtUtil.reissueToken(refreshToken));
	}

}
