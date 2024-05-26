package com.ddabong.ddabongdotchiBE.domain.blacklist.exception;

import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;
import com.ddabong.ddabongdotchiBE.domain.global.CustomException;

public class BlacklistExceptionHandler extends CustomException {
	public BlacklistExceptionHandler(BaseErrorCode code) {
		super(code);
	}
}
