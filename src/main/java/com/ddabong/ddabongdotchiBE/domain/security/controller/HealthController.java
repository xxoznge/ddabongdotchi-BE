package com.ddabong.ddabongdotchiBE.domain.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HealthController {
	@GetMapping("/api/v1/user/health")
	public String healthCheck() {
		return "OK";
	}
}
