package ru.blinov.language.spotter.country;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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
public class CountryRepositoryTest {
	
	@Autowired
	private CountryRepository sut;

	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Test
	public void Should_retrieve_all_country_entities_by_the_given_language_name() {
		
		//Arrange
		String languageName  = "English";
		int expectedNumberOfCountries = 3;
		
		//Act
		List<Country> countries = sut.findAllByLanguageName(languageName);
		
		//Assert
		assertThat(countries).hasSize(expectedNumberOfCountries);
		assertThat(countries).allMatch(country -> country.getLanguages().stream()
				.anyMatch(language -> language.getName().equals(languageName)));
	}
	
	@Test
	public void Should_not_find_any_country_entities_by_the_given_language_name() {
		
		//Arrange
		String languageName  = "Englishh";
		
		//Act
		List<Country> countries = sut.findAllByLanguageName(languageName);
		
		//Assert
		assertThat(countries).isEmpty();
	}

	@Test
	public void Should_retrieve_a_country_entity_by_the_given_name() {
		
		//Arrange
		String countryName = "Ireland";
		
		//Act
		Optional<Country> countryOptional = sut.findByName(countryName);
		
		//Assert
		assertThat(countryOptional.get().getName()).isEqualTo(countryName);
	}
	
	@Test
	public void Should_not_find_a_country_entity_by_the_given_name() {
		
		//Arrange
		String countryName = "Irelandd";
		
		//Act
		Optional<Country> countryOptional = sut.findByName(countryName);
		
		//Assert
		assertThat(countryOptional).isEmpty();
	}
}
