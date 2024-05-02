package com.ddabong.ddabongdotchiBE.domain.auth.jwt.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.ddabong.ddabongdotchiBE.domain.auth.jwt.dto.JwtDto;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.userdetails.PrincipalDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	private final SecretKey secretKey;
	private final Long accessExpMs;
	private final Long refreshExpMs;
	private final RedisUtil redisUtil;

	public JwtUtil(
		@Value("${spring.jwt.secret}") String secret,
		@Value("${spring.jwt.token.access-expiration-time}") Long access,
		@Value("${spring.jwt.token.refresh-expiration-time}") Long refresh,
		RedisUtil redis) {
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
		accessExpMs = access;
		refreshExpMs = refresh;
		redisUtil = redis;
	}

	public String getUsername(String token) throws SignatureException {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
	}

	public String getRoles(String token) throws SignatureException{
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);
	}

	public long getExpTime(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.getTime();
	}

	public String tokenProvider(PrincipalDetails principalDetails, Instant expiration) {
		Instant issuedAt = Instant.now();
		String authorities = principalDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.header()
			.add("typ", "JWT")
			.and()
			.subject(principalDetails.getUsername())
			.claim("role", authorities)
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();
	}

	public String createJwtAccessToken(PrincipalDetails principalDetails) {
		Instant expiration = Instant.now().plusMillis(accessExpMs);
		return tokenProvider(principalDetails, expiration);
	}

	public String createJwtRefreshToken(PrincipalDetails principalDetails) {
		Instant expiration = Instant.now().plusMillis(refreshExpMs);
		String refreshToken = tokenProvider(principalDetails, expiration);

		redisUtil.save(
			principalDetails.getUsername(),
			refreshToken,
			refreshExpMs,
			TimeUnit.MILLISECONDS
		);

		return refreshToken;
	}

	public JwtDto reissueToken(String refreshToken) throws SignatureException {
		isRefreshToken(refreshToken);

		PrincipalDetails userDetails = new PrincipalDetails(
			getUsername(refreshToken),
			null,
			getRoles(refreshToken)
		);
		log.info("[*] Token Reissue");

		return new JwtDto(
			createJwtAccessToken(userDetails),
			createJwtRefreshToken(userDetails)
		);
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

	public void isRefreshToken(String refreshToken) {
		String username = getUsername(refreshToken);
		//redis 확인
		String redisRefreshToken = redisUtil.get(username).toString();
		if (!refreshToken.equals(redisRefreshToken)) {
			log.warn("[*] case : Invalid refreshToken");
			throw new NoSuchElementException("Redis에 " + username + "에 해당하는 키가 없습니다.");
		}

		validateToken(refreshToken);
	}

	public void validateToken(String token) {
		try {
			long seconds = 3 *60;
			boolean isExpired = Jwts
				.parser()
				.clockSkewSeconds(seconds)
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration()
				.before(new Date());
			log.info("[*] Authorization with Token");
			if (isExpired) {
				// Todo: 에러 처리 필요
				log.info("만료된 JWT 토큰입니다.");
			}
		} catch (SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		}
	}

}
