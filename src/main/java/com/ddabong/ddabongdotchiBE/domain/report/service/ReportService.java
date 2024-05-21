package com.ddabong.ddabongdotchiBE.domain.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.report.dto.request.ReportCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.report.dto.response.ReportCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;
import com.ddabong.ddabongdotchiBE.domain.report.exception.ReportErrorCode;
import com.ddabong.ddabongdotchiBE.domain.report.exception.ReportExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.report.repository.ReportRepository;
import com.ddabong.ddabongdotchiBE.domain.security.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {

	private final ReportRepository reportRepository;

	public ReportCreateResponse createReport(User authUser, ReportCreateRequest request) {
		if (reportRepository.existsByTarget(request.target())) {
			throw new ReportExceptionHandler(ReportErrorCode.USER_ALREADY_REPORTED);
		}

		Report report = reportRepository.save(request.toEntity());
		return ReportCreateResponse.from(report);
	}
}
