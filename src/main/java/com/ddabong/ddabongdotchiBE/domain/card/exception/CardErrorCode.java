package com.ddabong.ddabongdotchiBE.domain.card.exception;

import org.springframework.http.HttpStatus;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardErrorCode implements BaseErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR4000", "존재하지 않는 사용자입니다."),
	CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "CARD4040", "해당 카드가 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
