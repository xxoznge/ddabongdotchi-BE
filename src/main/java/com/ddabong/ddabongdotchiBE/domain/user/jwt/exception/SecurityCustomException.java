package com.ddabong.ddabongdotchiBE.domain.user.jwt.exception;

import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;
import com.ddabong.ddabongdotchiBE.domain.global.CustomException;

import lombok.Getter;

@Getter
public class SecurityCustomException extends CustomException {

	private final Throwable cause;

	public SecurityCustomException(BaseErrorCode errorCode) {
		super(errorCode);
		this.cause = null;
	}

	public SecurityCustomException(BaseErrorCode errorCode, Throwable cause) {
		super(errorCode);
		this.cause = cause;
	}
}
