package com.ddabong.ddabongdotchiBE.domain.auth.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ddabong.ddabongdotchiBE.domain.auth.dto.LoginUserRequest;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.dto.JwtDto;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.userdetails.PrincipalDetails;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.HttpResponseUtil;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		log.info("JwtAuthenticationFilter : 로그인 시도 중");

		ObjectMapper om = new ObjectMapper();
		LoginUserRequest loginUserRequest;
		try {
			loginUserRequest = om.readValue(request.getInputStream(), LoginUserRequest.class);
		} catch (IOException e) {
			throw new AuthenticationServiceException("Error of request body.");
		}

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(
				loginUserRequest.username(),
				loginUserRequest.password());

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException {

		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

		log.info("[*] Login Success! - Login with " + principalDetails.getUsername());
		JwtDto jwtDto = new JwtDto(
			jwtUtil.createJwtAccessToken(principalDetails),
			jwtUtil.createJwtRefreshToken(principalDetails)
		);

		log.info("Access Token: " + jwtDto.accessToken());
		log.info("Refresh Token: " + jwtDto.refreshToken());

		HttpResponseUtil.setSuccessResponse(response, HttpStatus.CREATED, jwtDto);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed)
		throws IOException {

		String errorMessage;
		if (failed instanceof BadCredentialsException) {
			errorMessage = "Bad credentials";
		} else if (failed instanceof LockedException) {
			errorMessage = "Account is locked";
		} else if (failed instanceof DisabledException) {
			errorMessage = "Account is disabled";
		} else if (failed instanceof UsernameNotFoundException) {
			errorMessage = "Account not found";
		} else if (failed instanceof AuthenticationServiceException) {
			errorMessage = "Error occurred while parsing request body";
		} else {
			errorMessage = "Authentication failed";
		}
		log.info("[*] Login Fail - " + errorMessage);

		HttpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, errorMessage);

	}

}
