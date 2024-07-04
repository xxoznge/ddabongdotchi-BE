package com.ddabong.ddabongdotchiBE.domain.user.jwt.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.util.HttpResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		log.warn("[*] Access Denied: ", accessDeniedException);

		HttpResponseUtil.setErrorResponse(response,
			HttpStatus.FORBIDDEN,
			ApiResponse.onFailure(
				TokenErrorCode.FORBIDDEN.getCode(),
				TokenErrorCode.FORBIDDEN.getMessage(),
				accessDeniedException.getMessage()
			)
		);
	}
}
