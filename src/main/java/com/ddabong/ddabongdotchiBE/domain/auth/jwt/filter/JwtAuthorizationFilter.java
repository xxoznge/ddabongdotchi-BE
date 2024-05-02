package com.ddabong.ddabongdotchiBE.domain.auth.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ddabong.ddabongdotchiBE.domain.auth.jwt.userdetails.PrincipalDetails;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.HttpResponseUtil;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		log.info("[*] Jwt Filter");

		try {
			String accessToken = jwtUtil.resolveAccessToken(request);

			// accessToken 없이 접근할 경우
			if (accessToken == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// logout 처리된 accessToken
			if (redisUtil.get(accessToken) != null && redisUtil.get(accessToken).equals("logout")) {
				logger.info("[*] Logout accessToken");
				filterChain.doFilter(request, response);
				return;
			}

			log.info("[*] Authorization with Token");
			authenticateAccessToken(accessToken);

			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			try {
				HttpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, "엑세스 토큰이 유효하지 않습니다.");
			} catch (IOException ex) {
				log.error("IOException occurred while setting error response: {}", ex.getMessage());
			}
			log.warn("[*] case : accessToken Expired");
		}
	}

	private void authenticateAccessToken(String accessToken) {
		jwtUtil.validateToken(accessToken);

		PrincipalDetails principalDetails = new PrincipalDetails(
			jwtUtil.getUsername(accessToken),
			null,
			jwtUtil.getRoles(accessToken)
		);

		log.info("[*] Authority Registration");

		Authentication authToken = new UsernamePasswordAuthenticationToken(
			principalDetails,
			null,
			principalDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}
