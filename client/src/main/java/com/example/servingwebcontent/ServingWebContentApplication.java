package com.example.servingwebcontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Create Spring Boot Application and set a default controller
 */

@SpringBootApplication
public final class ServingWebContentApplication {

	private ServingWebContentApplication() { }
	public static void main(final String[] args) {
		SpringApplication.run(ServingWebContentApplication.class, args);
	}

}
