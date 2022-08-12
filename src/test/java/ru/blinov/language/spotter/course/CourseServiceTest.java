package ru.blinov.language.spotter.course;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.util.StringFormatter;
import ru.blinov.language.spotter.validator.RequestValidator;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private RequestValidator requestValidator;
	
	@InjectMocks
	private CourseService sut;
	
	@Test
	public void Should_get_all_courses_entities() {
		
		//Arrange
		List<Course> courses = Lists.newArrayList(new Course(), new Course());
		
		when(courseRepository.findAll()).thenReturn(courses);
		
		int expectedNumberOfCourses = courses.size();
		
		//Act
		List<Course> result = sut.findAllCourses();
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCourses);		
	}
	
	@Test
	public void Should_get_all_course_entities_by_language_name_and_country_name_and_city_name_and_education_center_name() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		String centerNamePathVariable = "erin_school_of_english";
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		
		Language language = new Language();
		language.setName("English");	
		Country country = new Country();
		country.setName("Ireland");
		City city = new City();
		city.setName("Dublin");
		city.setCountry(country);
		EducationCenter center = new EducationCenter();
		center.setName("Erin School Of English");
		center.setCity(city);
		
		Course courseOne = new Course();
		courseOne.setLanguage(language);
		courseOne.setEducationCenter(center);
		Course courseTwo = new Course();
		courseTwo.setLanguage(language);
		courseTwo.setEducationCenter(center);

		List<Course> courses = Lists.newArrayList(courseOne, courseTwo);
	
		when(courseRepository.findAllByLanguageNameAndCenterName(languageName, centerName)).thenReturn(courses);
		
		int expectedNumberOfCourses = courses.size();
		
		//Act
		List<Course> result = sut.findAllCourses(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable);
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCourses);
		assertThat(result).allMatch(c -> c.getLanguage().getName().equals(languageName)
				&& c.getEducationCenter().getName().equals(centerName)
				&& c.getEducationCenter().getCity().getName().equals(cityName)
				&& c.getEducationCenter().getCity().getCountry().getName().equals(countryName));
	}
	
	@Test
	public void Should_get_a_course_entity_by_the_given_id() {
		
		//Arrange
		Course course = new Course();
		Integer courseId = 1;
		course.setId(courseId);
		
		when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
		
		int expectedCourseId = courseId;

		//Act
		Course result = sut.findCourse(courseId);
		
		//Assert
		assertThat(result.getId()).isEqualTo(expectedCourseId);
	}
	
	@Test
	public void When_trying_to_get_a_course_entity_by_the_given_id_then_should_throw_requestUrlException_COURSE_NOT_FOUND() {
		
		//Arrange
		Integer courseId = 999;
		when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.COURSE_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.findCourse(courseId));
	}
	
	@Test
	public void Should_save_the_given_course_entity() {
		
		//Arrange
		Course course = new Course();
		course.setId(1);
		course.setCourseType("General English");
		
		ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);

		//Act
		sut.saveCourse(course);
		
		//Assert
		verify(courseRepository).save(captor.capture());
		assertThat(course).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_delete_a_course_entity_by_the_given_id() {
		
		//Arrange
		Course course = new Course();
		Integer courseId = 1;
		course.setId(courseId);
		
		when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
		
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		
		//Act
		sut.deleteCourse(courseId);
		
		//Assert
		verify(courseRepository).deleteById(captor.capture());
		assertThat(courseId).isEqualTo(captor.getValue());
	}
}
