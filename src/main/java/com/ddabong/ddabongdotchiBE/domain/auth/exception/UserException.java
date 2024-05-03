package com.ddabong.ddabongdotchiBE.domain.auth.exception;

import com.ddabong.ddabongdotchiBE.global.common.BaseErrorCode;
import com.ddabong.ddabongdotchiBE.global.exception.CustomException;

public class UserException extends CustomException {

	public UserException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
