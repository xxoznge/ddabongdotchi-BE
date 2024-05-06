package com.ddabong.ddabongdotchiBE.domain.security.jwt.util;

import static com.ddabong.ddabongdotchiBE.domain.security.jwt.exception.TokenErrorCode.*;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ddabong.ddabongdotchiBE.domain.security.jwt.exception.SecurityCustomException;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.exception.TokenErrorCode;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.userdetails.CustomUserDetails;
import com.ddabong.ddabongdotchiBE.domain.security.jwt.userdetails.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import umc.springumc.security.jwt.dto.JwtDto;

@Slf4j
@Component
public class JwtUtil {

	private final static String USERNAME = "username";
	private final static String IS_STAFF = "USER";
	private final CustomUserDetailsService customUserDetailsService;
	private final SecretKey secretKey;
	private final Long accessExpMs;
	private final Long refreshExpMs;
	private final RedisUtil redisUtil;

	public JwtUtil(
		CustomUserDetailsService customUserDetailsService, @Value("${jwt.secret}") String secret,
		@Value("${jwt.token.access-expiration-time}") Long access,
		@Value("${jwt.token.refresh-expiration-time}") Long refresh,
		RedisUtil redis) {
		this.customUserDetailsService = customUserDetailsService;

		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
		accessExpMs = access;
		refreshExpMs = refresh;
		redisUtil = redis;
	}

	public String getUsername(String token) throws SignatureException {
		return Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get(USERNAME, String.class);
	}

	public String isStaff(String token) throws SignatureException {
		return (String)Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get(IS_STAFF);
	}

	public boolean isExpired(String token) throws SignatureException {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.before(new Date());
	}

	public long getExpTime(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.getTime();
	}

	public String createJwtAccessToken(CustomUserDetails customUserDetails) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(accessExpMs);

		return Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.claim(USERNAME, customUserDetails.getUsername())
			.claim(IS_STAFF, customUserDetails.getStaff())
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();
	}

	public String createJwtRefreshToken(CustomUserDetails customUserDetails) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(refreshExpMs);

		String refreshToken = Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.claim(USERNAME, customUserDetails.getUsername())
			.claim(IS_STAFF, customUserDetails.getStaff())
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();

		redisUtil.save(
			customUserDetails.getUsername(),
			refreshToken,
			refreshExpMs,
			TimeUnit.MILLISECONDS
		);
		return refreshToken;
	}

	public JwtDto reissueToken(String refreshToken) {
		try {
			validateRefreshToken(refreshToken);
			log.info("[*] Valid RefreshToken");

			CustomUserDetails tempCustomUserDetails = new CustomUserDetails(
				getEmail(refreshToken),
				null,
				getAuthority(refreshToken)
			);

			return new JwtDto(
				createJwtAccessToken(tempCustomUserDetails),
				createJwtRefreshToken(tempCustomUserDetails)
			);
		} catch (IllegalArgumentException iae) {
			throw new SecurityCustomException(INVALID_TOKEN, iae);
		} catch (ExpiredJwtException eje) {
			throw new SecurityCustomException(TOKEN_EXPIRED, eje);
		}
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
		} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
			throw new SecurityCustomException(INVALID_TOKEN, e);
		} catch (SignatureException e) {
			throw new SecurityCustomException(TOKEN_SIGNATURE_ERROR, e);
		}
	}

	public Long getId(String token) {
		return Long.parseLong(getClaims(token).getSubject());
	}

	public String getEmail(String token) {
		return getClaims(token).get("email", String.class);
	}

	public String getAuthority(String token) {
		return getClaims(token).get("auth", String.class);
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");

		if (authorization == null || !authorization.startsWith("Bearer ")) {

			log.warn("[*] No Token in req");

			return null;
		}

		log.info("[*] Token exists");

		return authorization.split(" ")[1];
	}

	public boolean validateRefreshToken(String refreshToken) {

		// refreshToken validate
		String username = getUsername(refreshToken);

		//redis 확인
		if (!redisUtil.hasKey(username) || isExpired(refreshToken)) {
			throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN);
		}
		return true;
	}

}
