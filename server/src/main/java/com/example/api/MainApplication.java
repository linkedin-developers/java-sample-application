package com.example.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
 * Create Spring Boot Application and set a default controller
 */

@SpringBootApplication
public class MainApplication {
    public MainApplication() { }
    public static void main(final String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
