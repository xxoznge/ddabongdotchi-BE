package com.ddabong.ddabongdotchiBE.domain.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddabong.ddabongdotchiBE.domain.report.dto.request.ReportCreateRequest;
import com.ddabong.ddabongdotchiBE.domain.report.dto.response.ReportCreateResponse;
import com.ddabong.ddabongdotchiBE.domain.report.entity.Report;
import com.ddabong.ddabongdotchiBE.domain.report.exception.ReportErrorCode;
import com.ddabong.ddabongdotchiBE.domain.report.exception.ReportExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.report.repository.ReportRepository;
import com.ddabong.ddabongdotchiBE.domain.user.entity.User;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserErrorCode;
import com.ddabong.ddabongdotchiBE.domain.user.exception.UserExceptionHandler;
import com.ddabong.ddabongdotchiBE.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;

	public ReportCreateResponse createReport(User user, ReportCreateRequest request) {

		if (user.getUsername().equals(request.target()))
			throw new ReportExceptionHandler(ReportErrorCode.REPORT_ERROR);

		final User target = userRepository.findByUsername(request.target())
			.orElseThrow(() -> new UserExceptionHandler(UserErrorCode.USER_NOT_FOUND));

		if (reportRepository.existsByUserAndTarget(user, target))
			throw new ReportExceptionHandler(ReportErrorCode.USER_ALREADY_REPORTED);

		Report report = reportRepository.save(request.toEntity(user, target));
		return ReportCreateResponse.from(report);
	}
}
