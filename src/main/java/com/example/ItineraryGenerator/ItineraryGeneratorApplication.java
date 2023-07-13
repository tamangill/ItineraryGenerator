package com.example.ItineraryGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.ItineraryGenerator")
public class ItineraryGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItineraryGeneratorApplication.class, args);
	}		
}
