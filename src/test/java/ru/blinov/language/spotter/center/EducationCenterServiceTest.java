package ru.blinov.language.spotter.center;

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

import ru.blinov.language.spotter.accommodation.Accommodation;
import ru.blinov.language.spotter.accommodation.AccommodationService;
import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseService;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageService;
import ru.blinov.language.spotter.util.StringFormatter;
import ru.blinov.language.spotter.validator.RequestValidator;

@ExtendWith(MockitoExtension.class)
public class EducationCenterServiceTest {
	
	@Mock
	private LanguageService languageService;
	
	@Mock
	private CourseService courseService;
	
	@Mock
	private AccommodationService accommodationService;
	
	@Mock
	private EducationCenterRepository educationCenterRepository;
	
	@Mock
	private RequestValidator requestValidator;
	
	@InjectMocks
	private EducationCenterService sut;
	
	@Test
	public void Should_get_all_education_center_entities() {
		
		//Arrange
		List<EducationCenter> centers = Lists.newArrayList(new EducationCenter(), new EducationCenter());
		
		when(educationCenterRepository.findAll()).thenReturn(centers);
		
		int expectedNumberOfCenters = centers.size();
		
		//Act
		List<EducationCenter> result = sut.findAllEducationCenters();
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCenters);		
	}
	
	@Test
	public void Should_get_all_education_center_entities_by_language_name_and_country_name_and_city_name() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		
		Language language = new Language();
		language.setName("English");	
		Country country = new Country();
		country.setName("Ireland");
		City city = new City();
		city.setName("Dublin");
		city.setCountry(country);
		
		EducationCenter centerOne = new EducationCenter();
		centerOne.setLanguages(Lists.newArrayList(language));
		centerOne.setCity(city);
		EducationCenter centerTwo = new EducationCenter();
		centerTwo.setLanguages(Lists.newArrayList(language));
		centerTwo.setCity(city);

		List<EducationCenter> centers = Lists.newArrayList(centerOne, centerTwo);
	
		when(educationCenterRepository.findAllByLanguageNameAndCityName(languageName, cityName)).thenReturn(centers);
		
		int expectedNumberOfCenters = centers.size();
		
		//Act
		List<EducationCenter> result = sut.findAllEducationCenters(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable);
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCenters);
		assertThat(result).allMatch(c -> c.hasLanguage(languageName)
				&& c.getCity().getName().equals(cityName)
				&& c.getCity().getCountry().getName().equals(countryName));
	}
	
	@Test
	public void Should_get_an_education_center_entity_by_the_given_name() {
		
		//Arrange
		EducationCenter center = new EducationCenter();
		String centerNamePathVariable = "english";
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);	
		center.setName(centerName);
		
		when(educationCenterRepository.findByName(centerName)).thenReturn(Optional.of(center));
		
		String expectedCenterName = centerName;

		//Act
		EducationCenter result = sut.findEducationCenter(centerNamePathVariable);
		
		//Assert
		assertThat(result.getName()).isEqualTo(expectedCenterName);
	}
	
	@Test
	public void When_trying_to_get_an_education_center_entity_by_the_given_name_then_should_throw_requestUrlException_EDUCATION_CENTER_NOT_FOUND() {
		
		//Arrange
		String centerNamePathVariable = "englishh";
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);	
		when(educationCenterRepository.findByName(centerName)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.findEducationCenter(centerNamePathVariable));
	}
	
	@Test
	public void Should_get_an_education_center_entity_by_the_given_id() {
		
		//Arrange
		EducationCenter center = new EducationCenter();
		Integer centerId = 999;
		center.setId(centerId);
		
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));
		
		int expectedCenterId = centerId;

		//Act
		EducationCenter result = sut.findEducationCenter(centerId);
		
		//Assert
		assertThat(result.getId()).isEqualTo(expectedCenterId);
	}
	
	@Test
	public void When_trying_to_get_an_education_center_entity_by_the_id_then_should_throw_requestUrlException_EDUCATION_CENTER_NOT_FOUND() {
		
		//Arrange
		Integer centerId = 999;	
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.findEducationCenter(centerId));
	}
	
	@Test
	public void Should_save_the_given_education_center_entity() {
		
		//Arrange
		EducationCenter center = new EducationCenter();
		center.setId(1);
		center.setName("Erin School Of English");
		
		ArgumentCaptor<EducationCenter> captor = ArgumentCaptor.forClass(EducationCenter.class);

		//Act
		sut.saveEducationCenter(center);
		
		//Assert
		verify(educationCenterRepository).save(captor.capture());
		assertThat(center).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_add_language_to_education_center() {
		
		//Arrange
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setLanguages(Lists.newArrayList());
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));
		
		Language language = new Language();
		Integer languageId = 1;
		language.setId(languageId);
		when(languageService.findLanguage(languageId)).thenReturn(language);

		//Act
		sut.addLanguageToEducationCenter(centerId, languageId);
			
		//Assert
		assertThat(center.getLanguages()).anyMatch(l -> l.getId() == languageId);
	}

	@Test
	public void Should_add_course_to_education_center() {
		
		//Arrange
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setCourses(Lists.newArrayList());
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));
		
		Course course = new Course();
		Integer courseId = 1;
		course.setId(courseId);
		when(courseService.findCourse(courseId)).thenReturn(course);

		//Act
		sut.addCourseToEducationCenter(centerId, courseId);
			
		//Assert
		assertThat(center.getCourses()).anyMatch(c -> c.getId() == courseId);
	}
	
	@Test
	public void Should_add_accommodation_to_education_center() {
		
		//Arrange
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setAccommodations(Lists.newArrayList());
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));
		
		Accommodation accommodation = new Accommodation();
		Integer accommodationId = 1;
		accommodation.setId(accommodationId);
		when(accommodationService.findAccommodation(accommodationId)).thenReturn(accommodation);

		//Act
		sut.addAccommodationToEducationCenter(centerId, accommodationId);
			
		//Assert
		assertThat(center.getAccommodations()).anyMatch(a -> a.getId() == accommodationId);
	}
	
	@Test
	public void Should_delete_an_education_center_entity_by_the_given_id() {
		
		//Arrange
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));
		
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		
		//Act
		sut.deleteEducationCenter(centerId);
		
		//Assert
		verify(educationCenterRepository).deleteById(captor.capture());
		assertThat(centerId).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_remove_language_from_education_center() {
		
		//Arrange
		Language languageOne = new Language();
		Integer languageOneId = 1;
		languageOne.setId(languageOneId);
		
		Language languageTwo = new Language();
		Integer languageTwoId = 2;
		languageTwo.setId(languageTwoId);
		
		when(languageService.findLanguage(languageOneId)).thenReturn(languageOne);
		
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setLanguages(Lists.newArrayList(languageOne, languageTwo));
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));

		//Act
		sut.removeLanguageFromEducationCenter(centerId, languageOneId);
			
		//Assert
		assertThat(center.getLanguages()).anyMatch(l -> l.getId() != languageOneId);
		assertThat(center.getLanguages()).anyMatch(l -> l.getId() == languageTwoId);
	}
	
	@Test
	public void Should_remove_course_from_education_center() {
		
		//Arrange
		Course courseOne = new Course();
		Integer courseOneId = 1;
		courseOne.setId(courseOneId);
		
		Course courseTwo = new Course();
		Integer courseTwoId = 2;
		courseTwo.setId(courseTwoId);
		
		when(courseService.findCourse(courseOneId)).thenReturn(courseOne);
		
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setCourses(Lists.newArrayList(courseOne, courseTwo));
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));

		//Act
		sut.removeCourseFromEducationCenter(centerId, courseOneId);
			
		//Assert
		assertThat(center.getCourses()).anyMatch(c -> c.getId() != courseOneId);
		assertThat(center.getCourses()).anyMatch(c -> c.getId() == courseTwoId);
	}
	
	@Test
	public void Should_remove_accommodation_from_education_center() {
		
		//Arrange
		Accommodation accommodationOne = new Accommodation();
		Integer accommodationOneId = 1;
		accommodationOne.setId(accommodationOneId);
		
		Accommodation accommodationTwo = new Accommodation();
		Integer accommodationTwoId = 2;
		accommodationTwo.setId(accommodationTwoId);
		
		when(accommodationService.findAccommodation(accommodationOneId)).thenReturn(accommodationOne);
		
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setAccommodations(Lists.newArrayList(accommodationOne, accommodationTwo));
		when(educationCenterRepository.findById(centerId)).thenReturn(Optional.of(center));

		//Act
		sut.removeAccommodationFromEducationCenter(centerId, accommodationOneId);
			
		//Assert
		assertThat(center.getAccommodations()).anyMatch(c -> c.getId() != accommodationOneId);
		assertThat(center.getAccommodations()).anyMatch(c -> c.getId() == accommodationTwoId);
	}
}
