package com.ddabong.ddabongdotchiBE.domain.global;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final BaseErrorCode errorCode;

	public CustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
