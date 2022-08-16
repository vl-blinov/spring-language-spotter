package ru.blinov.language.spotter.center;

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
public class EducationCenterRepositoryTest {
	
	@Autowired
	private EducationCenterRepository sut;

	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Test
	public void Should_retrieve_all_education_center_entities_by_the_given_language_name_and_city_name() {
		
		//Arrange
		String languageName = "English";
		
		String cityName = "Dublin";
		
		int expectedNumberOfCenters = 3;
		
		//Act
		List<EducationCenter> centers = sut.findAllByLanguageNameAndCityName(languageName, cityName);
		
		//Assert
		assertThat(centers).hasSize(expectedNumberOfCenters);
		
		assertThat(centers).allMatch(center -> center.getLanguages().stream()
				.anyMatch(language -> language.getName().equals(languageName)));
		
		assertThat(centers).allMatch(center -> center.getCity().getName().equals(cityName));
	}
	
	@Test
	public void Should_not_find_any_education_center_entities_by_the_given_language_name_and_city_name() {
		
		//Arrange
		String languageName = "English";
		String cityName = "Dublinn";
		
		//Act
		List<EducationCenter> centers = sut.findAllByLanguageNameAndCityName(languageName, cityName);
		
		//Assert
		assertThat(centers).isEmpty();
	}
	
	@Test
	public void Should_retrieve_an_education_center_entity_by_the_given_name() {
		
		//Arrange
		String centerName = "University College Dublin";
		
		//Act
		Optional<EducationCenter> centerOptional = sut.findByName(centerName);
		
		//Assert
		assertThat(centerOptional.get().getName()).isEqualTo(centerName);
	}
	
	@Test
	public void Should_not_find_an_education_center_entity_by_the_given_name() {
		
		//Arrange
		String centerName = "University College Dublinn";
		
		//Act
		Optional<EducationCenter> centerOptional = sut.findByName(centerName);
		
		//Assert
		assertThat(centerOptional).isEmpty();
	}
}
