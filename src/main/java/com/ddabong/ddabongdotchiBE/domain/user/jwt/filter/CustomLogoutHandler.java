package com.ddabong.ddabongdotchiBE.domain.user.jwt.filter;

import java.util.concurrent.TimeUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.ddabong.ddabongdotchiBE.domain.user.jwt.exception.SecurityCustomException;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.exception.TokenErrorCode;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.user.jwt.util.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

	private final RedisUtil redisUtil;
	private final JwtUtil jwtUtil;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			log.info("[*] Logout Filter");

			String accessToken = jwtUtil.resolveAccessToken(request);

			// if (redisUtil.hasKey(accessToken)) {
			// 	log.info("=====================================");
			// 	throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN);
			// }

			redisUtil.saveAsValue(
				accessToken,
				"logout",
				jwtUtil.getExpTime(accessToken),
				TimeUnit.MILLISECONDS
			);

			redisUtil.delete(
				jwtUtil.getUsername(accessToken) + "_refresh_token"
			);

		} catch (ExpiredJwtException e) {
			log.warn("[*] case : accessToken expired");
			throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED);
		}
	}
}
