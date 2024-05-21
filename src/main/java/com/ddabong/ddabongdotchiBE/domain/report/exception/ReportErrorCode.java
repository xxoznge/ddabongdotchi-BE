package com.ddabong.ddabongdotchiBE.domain.report.exception;

import org.springframework.http.HttpStatus;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements BaseErrorCode {

	USER_ALREADY_REPORTED(HttpStatus.CONFLICT, "REPORT4009", "이미 신고한 사용자입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
