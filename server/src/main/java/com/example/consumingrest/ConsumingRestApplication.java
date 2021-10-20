package com.example.consumingrest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
 * Create Spring Boot Application and set a default controller
 */

@SpringBootApplication
public final class ConsumingRestApplication {
    
    private ConsumingRestApplication() { }
    
    public static void main(final String[] args) {
        SpringApplication.run(ConsumingRestApplication.class, args);
    }

}
