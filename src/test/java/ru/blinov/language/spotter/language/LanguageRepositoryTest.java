package ru.blinov.language.spotter.language;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
public class LanguageRepositoryTest {
	
	@Autowired
	private LanguageRepository sut;

	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Test
	public void Should_retrieve_a_language_entity_by_the_given_name() {
		
		//Arrange
		String languageName = "English";
		
		//Act
		Optional<Language> languageOptional = sut.findByName(languageName);

		//Assert
		assertThat(languageOptional.get().getName()).isEqualTo(languageName);
	}
	
	@Test
	public void Should_not_find_a_language_entity_by_the_given_name() {
		
		//Arrange
		String languageName = "Englishh";
		
		//Act
		Optional<Language> languageOptional = sut.findByName(languageName);
		
		//Assert
		assertThat(languageOptional).isEmpty();
	}
}
