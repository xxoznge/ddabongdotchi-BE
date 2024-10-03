package com.ddabong.ddabongdotchiBE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class DdabongdotchiBeApplication {

	public static void main(String[] args) {

		SpringApplication.run(DdabongdotchiBeApplication.class, args);

		long heapSize = Runtime.getRuntime().totalMemory();
		log.info("HEAP Size(M) : {}MB", heapSize / (1024 * 1024));
	}
}
