package com.ddabong.ddabongdotchiBE.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.ddabong.ddabongdotchiBE.global.common.ApiResponse;
import com.ddabong.ddabongdotchiBE.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
	USER_ERROR(HttpStatus.BAD_REQUEST, "USR4000", "사용자 관련 에러"),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "USR4001", "잘못된 형식입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR4040", "존재하지 않는 사용자입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
