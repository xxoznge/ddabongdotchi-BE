package com.ddabong.ddabongdotchiBE.domain.blacklist.exception;

import org.springframework.http.HttpStatus;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlacklistErrorCode implements BaseErrorCode {

	BLACKLIST_ERROR(HttpStatus.BAD_REQUEST, "BLK4000", "차단 관련 에러"),
	BLACKLIST_NOT_FOUND(HttpStatus.BAD_REQUEST, "BLK4001", "차단한 사용자가 아닙니다."),

	BLACKLIST_ALREADY_REPORTED(HttpStatus.CONFLICT, "BLK4009", "이미 차단한 사용자입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
