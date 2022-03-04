package com.linkedIn.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create Spring Boot Application and set a default controller
 */

@SpringBootApplication
public class Example {

	public Example() { }
	public static void main(final String[] args) {
		SpringApplication.run(Example.class, args);
	}

}
