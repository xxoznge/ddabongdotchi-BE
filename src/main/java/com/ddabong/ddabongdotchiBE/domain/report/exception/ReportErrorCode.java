package com.ddabong.ddabongdotchiBE.domain.report.exception;

import org.springframework.http.HttpStatus;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements BaseErrorCode {

	REPORT_ERROR(HttpStatus.BAD_REQUEST, "REPORT4000", "신고 관련 에러"),
	USER_ALREADY_REPORTED(HttpStatus.CONFLICT, "REPORT4009", "이미 신고한 사용자입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
