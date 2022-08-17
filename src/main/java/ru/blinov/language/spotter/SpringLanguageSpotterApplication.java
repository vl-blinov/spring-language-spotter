package ru.blinov.language.spotter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Language Spotter REST API", version = "1.0.0"))
public class SpringLanguageSpotterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLanguageSpotterApplication.class, args);
	}
}
