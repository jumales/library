package com.jumales.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/books/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("POST", "PUT", "GET", "DELETE");
				registry.addMapping("/api/v1/authors/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("POST", "PUT", "GET", "DELETE");
				registry.addMapping("/api/v1/auth/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("POST", "GET");
			}
		};
	}

}
