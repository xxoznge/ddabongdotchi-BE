package com.ddabong.ddabongdotchiBE.domain.card.exception;

import com.ddabong.ddabongdotchiBE.domain.global.BaseErrorCode;
import com.ddabong.ddabongdotchiBE.domain.global.CustomException;

public class CardExceptionHandler extends CustomException {
	public CardExceptionHandler(BaseErrorCode code) {
		super(code);
	}
}
