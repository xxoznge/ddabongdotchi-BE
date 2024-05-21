package com.ddabong.ddabongdotchiBE.domain.report.exception;

import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;
import com.ddabong.ddabongdotchiBE.domain.global.CustomException;

public class ReportExceptionHandler extends CustomException {
	public ReportExceptionHandler(BaseErrorCode code) {
		super(code);
	}
}

