package com.hope.apiapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class MainApplication {

	private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) {
		logger.debug("Debug level log: sampleEndpoint called");
		logger.info("Info level log: Returning sample response");
		logger.warn("Warning level log: Something might be wrong!");
		logger.error("Error level log: An error occurred!");

		SpringApplication.run(MainApplication.class, args);

	}

}
