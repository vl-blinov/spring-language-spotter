package ru.blinov.language.spotter.validator;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;

@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
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
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable));
	}
	
	@Test
	public void While_checking_language_should_throw_requestUrlException_LANGUAGE_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "englishh";
		
		String message = RequestUrlMessage.LANGUAGE_NOT_FOUND.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String countryNamePathVariable = "ireland";

		//Assert
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable));
	}
	
	@Test
	public void While_checking_language_and_country_should_throw_requestUrlException_COUNTRY_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "english";
		
		String countryNamePathVariable = "irelandd";
		
		String message = RequestUrlMessage.COUNTRY_NOT_FOUND.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable));
	}
	
	
	@Test
	@Transactional
	public void While_checking_language_and_country_should_throw_requestUrlException_COUNTRY_LANGUAGE_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "french";
		
		String countryNamePathVariable = "ireland";
		
		String message = RequestUrlMessage.COUNTRY_LANGUAGE_DISCR.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";
		
		String countryNamePathVariable = "ireland";
		
		String cityNamePathVariable = "dublin";

		//Assert
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_throw_requestUrlException_CITY_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "english";
		
		String countryNamePathVariable = "ireland";
		
		String cityNamePathVariable = "dublinn";
		
		String message = RequestUrlMessage.CITY_NOT_FOUND.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_throw_requestUrlException_CITY_COUNTRY_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "english";
		
		String countryNamePathVariable = "canada";
		
		String cityNamePathVariable = "dublin";
		
		String message = RequestUrlMessage.CITY_COUNTRY_DISCR.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_throw_requestUrlException_CITY_LANGUAGE_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "irish";
		
		String countryNamePathVariable = "ireland";
		
		String cityNamePathVariable = "cork";
		
		String message = RequestUrlMessage.CITY_LANGUAGE_DISCR.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";
		
		String countryNamePathVariable = "ireland";
		
		String cityNamePathVariable = "dublin";
		
		String centerNamePathVariable = "erin_school_of_english";

		//Assert
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_throw_requestUrlException_EDUCATION_CENTER_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "english";
		
		String countryNamePathVariable = "ireland";
		
		String cityNamePathVariable = "dublin";
		
		String centerNamePathVariable = "erin_school_of_englishh";
		
		String message = RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_throw_requestUrlException_EDUCATION_CENTER_CITY_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "english";
		
		String countryNamePathVariable = "ireland";
		
		String cityNamePathVariable = "dublin";
		
		String centerNamePathVariable = "cork_english_college";
		
		String message = RequestUrlMessage.EDUCATION_CENTER_CITY_DISCR.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_throw_requestUrlException_EDUCATION_CENTER_LANGUAGE_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "irish";
		
		String countryNamePathVariable = "ireland";
		
		String cityNamePathVariable = "dublin";
		
		String centerNamePathVariable = "erin_school_of_english";
		
		String message = RequestUrlMessage.EDUCATION_CENTER_LANGUAGE_DISCR.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}	
}
