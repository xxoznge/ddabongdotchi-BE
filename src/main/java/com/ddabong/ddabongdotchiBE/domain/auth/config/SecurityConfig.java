package com.ddabong.ddabongdotchiBE.domain.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ddabong.ddabongdotchiBE.domain.auth.jwt.filter.JwtAuthenticationFilter;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.filter.JwtAuthorizationFilter;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.filter.JwtLogoutFilter;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.HttpResponseUtil;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.JwtUtil;
import com.ddabong.ddabongdotchiBE.domain.auth.jwt.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	private final String[] allowedUrls = {"/", "/api/v1/user/reissue", "/api/v1/user/login"};

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// cors 비활성화
		http
			.cors(cors -> cors
				.configurationSource(CorsConfig.apiConfigurationSource()));

		// csrf disable
		http
			.csrf(AbstractHttpConfigurer::disable);

		// form 로그인 방식 disable
		http
			.formLogin(AbstractHttpConfigurer::disable);

		// http basic 인증 방식 disable
		http
			.httpBasic(AbstractHttpConfigurer::disable);

		// Session 사용하지 않고, Stateless 서버로
		http
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// 경로별 인가
		http.
			authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/api/v1/user/**").authenticated()
					.requestMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGE")
					.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
					.requestMatchers(allowedUrls).permitAll()
					.anyRequest().permitAll()
			);

		// JWT login
		// Jwt Filter (with login)
		JwtAuthenticationFilter loginFilter = new JwtAuthenticationFilter(
			authenticationManager(authenticationConfiguration), jwtUtil);
		loginFilter.setFilterProcessesUrl("/api/v1/user/login");

		http
			.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
		http
			.addFilterBefore(new JwtAuthorizationFilter(jwtUtil, redisUtil), JwtAuthenticationFilter.class);

		// Logout Filter
		http
			.logout(logout -> logout
				.logoutUrl("/api/v1/user/logout")
				.addLogoutHandler(new JwtLogoutFilter(redisUtil, jwtUtil))
				.logoutSuccessHandler((request, response, authentication) ->
					HttpResponseUtil.setSuccessResponse(
						response,
						HttpStatus.OK,
						"로그아웃 성공"
					)
				)
			);

		return http.build();
	}
}
