package com.ddabong.ddabongdotchiBE.global.exception;

import com.ddabong.ddabongdotchiBE.global.common.BaseErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final BaseErrorCode errorCode;

	public CustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
