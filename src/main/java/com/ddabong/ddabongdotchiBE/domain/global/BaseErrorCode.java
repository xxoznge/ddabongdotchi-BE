package com.ddabong.ddabongdotchiBE.domain.global;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	HttpStatus getHttpStatus();

	String getCode();

	String getMessage();

	ApiResponse<Void> getErrorResponse();
}
