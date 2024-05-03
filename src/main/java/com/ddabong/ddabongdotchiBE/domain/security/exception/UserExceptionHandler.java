package com.ddabong.ddabongdotchiBE.domain.security.exception;

import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;
import com.ddabong.ddabongdotchiBE.domain.global.CustomException;

public class UserExceptionHandler extends CustomException {
	public UserExceptionHandler(BaseErrorCode code) {
		super(code);
	}
}
