package ru.blinov.language.spotter.validator;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.util.StringFormatter;

@SpringBootTest
@Testcontainers
public class RequestValidatorTest {
	
	@Autowired
	private RequestValidator sut;
	
	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Test
	public void While_checking_language_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";

		//Assert
		assertDoesNotThrow(() -> sut.checkUrlPathVariables(languageNamePathVariable));
	}
	
	@Test
	public void While_checking_language_should_throw_requestUrlException_with_message_is_not_found() {
		
		//Arrange
		String languageNamePathVariable = "englishh";
		
		String message = "Language with name '" + StringFormatter.formatPathVariable(languageNamePathVariable) + "' is not found";

		//Assert
		assertThrows(message, RequestUrlException.class, () -> sut.checkUrlPathVariables(languageNamePathVariable));
	}
}
