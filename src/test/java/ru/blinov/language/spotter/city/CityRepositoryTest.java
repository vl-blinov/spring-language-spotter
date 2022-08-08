package ru.blinov.language.spotter.city;

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
public class CityRepositoryTest {
	
	@Autowired
	private CityRepository sut;

	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Test
	public void Should_retrieve_all_city_entities_by_the_given_language_name_and_country_name() {
		
		//Arrange
		String languageName = "English";
		
		String countryName = "Ireland";
		
		int expectedNumberOfCities = 2;
		
		//Act
		List<City> cities = sut.findAllByLanguageNameAndCountryName(languageName, countryName);
		
		//Assert
		assertThat(cities).hasSize(expectedNumberOfCities);
		
		assertThat(cities).allMatch(city -> city.getLanguages().stream()
				.anyMatch(language -> language.getName().equals(languageName)));
		
		assertThat(cities).allMatch(city -> city.getCountry().getName().equals(countryName));
	}
	
	@Test
	public void Should_not_find_any_city_entities_by_the_given_language_name_and_country_name() {
		
		//Arrange
		String languageName = "English";
		String countryName = "Irelandd";
		
		//Act
		List<City> cities = sut.findAllByLanguageNameAndCountryName(languageName, countryName);
		
		//Assert
		assertThat(cities).isEmpty();
	}

	@Test
	public void Should_retrieve_a_city_entity_by_the_given_name() {
		
		//Arrange
		String cityName = "Dublin";
		
		//Act
		Optional<City> cityOptional = sut.findByName(cityName);
		
		//Assert
		assertThat(cityOptional.get().getName()).isEqualTo(cityName);
	}
	
	@Test
	public void Should_not_find_a_city_entity_by_the_given_name() {
		
		//Arrange
		String cityName = "Dublinn";
		
		//Act
		Optional<City> cityOptional = sut.findByName(cityName);
		
		//Assert
		assertThat(cityOptional).isEmpty();
	}
}
