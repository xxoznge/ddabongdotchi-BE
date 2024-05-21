package com.ddabong.ddabongdotchiBE.domain.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.report.dto.request.ReportCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.report.dto.response.ReportCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.report.service.ReportService;
import com.ddabong.ddabongdotchiBE.domain.security.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@RestController
public class ReportController {

	private final ReportService reportService;

	@PostMapping("")
	public ResponseEntity<ReportCreateResponse> createReport(
		@UserResolver User authUser,
		@RequestBody ReportCreateRequest request
	) {
		return ResponseEntity.ok(reportService.createReport(authUser, request));
	}
}
