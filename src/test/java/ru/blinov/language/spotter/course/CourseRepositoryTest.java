package ru.blinov.language.spotter.course;

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
public class CourseRepositoryTest {
	
	@Autowired
	private CourseRepository sut;

	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	public static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}
	
	@Test
	public void Should_retrieve_all_course_entities_by_the_given_language_name_and_education_center_name() {
		
		//Arrange
		String languageName = "English";
		
		String centerName = "University College Dublin";
		
		int expectedNumberOfCourses = 1;
		
		//Act
		List<Course> courses = sut.findAllByLanguageNameAndCenterName(languageName, centerName);
		
		//Assert
		assertThat(courses).hasSize(expectedNumberOfCourses);
		
		assertThat(courses).allMatch(course -> course.getLanguage().getName().equals(languageName));
		
		assertThat(courses).allMatch(course -> course.getEducationCenter().getName().equals(centerName));
	}
	
	@Test
	public void Should_not_find_any_course_entities_by_the_given_language_name_and_education_center_name() {
		
		//Arrange
		String languageName = "English";
		String centerName = "University College Dublinn";
		
		//Act
		List<Course> courses = sut.findAllByLanguageNameAndCenterName(languageName, centerName);
		
		//Assert
		assertThat(courses).isEmpty();
	}
}
