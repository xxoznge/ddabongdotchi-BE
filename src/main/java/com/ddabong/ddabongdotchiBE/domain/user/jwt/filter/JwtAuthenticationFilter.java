package com.ddabong.ddabongdotchiBE.domain.user.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ddabong.ddabongdotchiBE.domain.user.jwt.exception.SecurityCustomException;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.exception.TokenErrorCode;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.userdetails.CustomUserDetails;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.util.RedisUtil;

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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

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
				log.info("[*] Logout accessToken");
				filterChain.doFilter(request, response);
				return;
			}

			log.info("[*] Authorization with Token");
			authenticateAccessToken(accessToken);
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			log.warn("[*] case : accessToken Expired");
			throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED);
		} catch (InsufficientAuthenticationException e) {
			log.warn("[*] case : FORBIDDEN");
			throw new SecurityCustomException(TokenErrorCode.FORBIDDEN);
		}
	}

	private void authenticateAccessToken(String accessToken) {
		CustomUserDetails userDetails = new CustomUserDetails(
			jwtUtil.getUsername(accessToken),
			null,
			jwtUtil.isStaff(accessToken),
			jwtUtil.userStatus(accessToken)
		);

		log.info("[*] Authority Registration");

		// 스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities());

		// 컨텍스트 홀더에 저장
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}
