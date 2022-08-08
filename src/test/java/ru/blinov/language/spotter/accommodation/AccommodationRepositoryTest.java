package ru.blinov.language.spotter.accommodation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
public class AccommodationRepositoryTest {

	@Autowired
	private AccommodationRepository sut;

	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Test
	public void Should_retrieve_all_accommodation_entities_by_the_given_education_center_name() {
		
		//Arrange
		String centerName = "Erin School Of English";
		int expectedNumberOfAccommodations = 2;
		
		//Act
		List<Accommodation> accommodations = sut.findAllByCenterName(centerName);
		
		//Assert
		assertThat(accommodations).hasSize(expectedNumberOfAccommodations);
		
		assertThat(accommodations).allMatch(accommodation -> accommodation.getEducationCenter().getName().equals(centerName));
	}
	
	@Test
	public void Should_not_find_any_accommodation_entities_by_the_given_education_center_name() {
		
		//Arrange
		String centerName = "Erin School Of Englishh";
		
		//Act
		List<Accommodation> accommodations = sut.findAllByCenterName(centerName);
		
		//Assert
		assertThat(accommodations).isEmpty();
	}
}
