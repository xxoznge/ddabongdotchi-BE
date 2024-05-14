package com.ddabong.ddabongdotchiBE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DdabongdotchiBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdabongdotchiBeApplication.class, args);
	}

}
