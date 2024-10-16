package com.ddabong.ddabongdotchiBE.domain.user.config;

import java.util.ArrayList;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CorsConfig implements WebMvcConfigurer {
	
	public static CorsConfigurationSource apiConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		ArrayList<String> allowedOriginPatterns = new ArrayList<>();
		allowedOriginPatterns.add("http://localhost:8080");
		allowedOriginPatterns.add("http://localhost:3000");
		allowedOriginPatterns.add("https://ddabong.xxoznge.site");

		ArrayList<String> allowedHttpMethods = new ArrayList<>();
		allowedHttpMethods.add("GET");
		allowedHttpMethods.add("POST");

		configuration.setAllowedOrigins(allowedOriginPatterns);
		configuration.setAllowedMethods(allowedHttpMethods);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
