package com.ddabong.ddabongdotchiBE.domain.report.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.report.dto.request.ReportCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.report.dto.response.ReportCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.report.service.ReportService;
import com.ddabong.ddabongdotchiBE.domain.user.annotation.UserResolver;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@RestController
public class ReportController {

	private final ReportService reportService;

	@PostMapping("")
	public ApiResponse<ReportCreateResponse> createReport(
		@UserResolver User authUser,
		@RequestBody ReportCreateRequest request
	) {
		return ApiResponse.onSuccess(reportService.createReport(authUser, request));
	}
}
