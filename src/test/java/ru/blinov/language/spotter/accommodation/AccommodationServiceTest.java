package ru.blinov.language.spotter.accommodation;

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
public class AccommodationServiceTest {
	
	@Mock
	private AccommodationRepository accommodationRepository;
	
	@Mock
	private RequestValidator requestValidator;
	
	@InjectMocks
	private AccommodationService sut;
	
	@Test
	public void Should_get_all_accommodation_entities() {
		
		//Arrange
		List<Accommodation> accommodations = Lists.newArrayList(new Accommodation(), new Accommodation());
		
		when(accommodationRepository.findAll()).thenReturn(accommodations);
		
		int expectedNumberOfAccommodations = accommodations.size();
		
		//Act
		List<Accommodation> result = sut.findAllAccommodations();
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfAccommodations);		
	}
	
	@Test
	public void Should_get_all_accommodation_entities_by_language_name_and_country_name_and_city_name_and_education_center_name() {
		
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
		
		Accommodation accommodationOne = new Accommodation();
		accommodationOne.setEducationCenter(center);
		Accommodation accommodationTwo = new Accommodation();
		accommodationTwo.setEducationCenter(center);
		
		List<Accommodation> accommodations = Lists.newArrayList(accommodationOne, accommodationTwo);
	
		when(accommodationRepository.findAllByCenterName(centerName)).thenReturn(accommodations);
		
		int expectedNumberOfAccommodations = accommodations.size();
		
		//Act
		List<Accommodation> result = sut.findAllAccommodations(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable,
				centerNamePathVariable);
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfAccommodations);
		assertThat(result).allMatch(c -> c.getEducationCenter().getName().equals(centerName)
				&& c.getEducationCenter().getCity().getName().equals(cityName)
				&& c.getEducationCenter().getCity().getCountry().getName().equals(countryName));
	}
	
	@Test
	public void Should_get_an_accommodation_entity_by_the_given_id() {
		
		//Arrange
		Accommodation accommodation = new Accommodation();
		Integer accommodationId = 1;
		accommodation.setId(accommodationId);
		
		when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));
		
		int expectedAccommodationId = accommodationId;

		//Act
		Accommodation result = sut.findAccommodation(accommodationId);
		
		//Assert
		assertThat(result.getId()).isEqualTo(expectedAccommodationId);
	}
	
	@Test
	public void When_trying_to_get_an_accommodation_entity_by_the_given_id_then_should_throw_requestUrlException_COURSE_NOT_FOUND() {
		
		//Arrange
		Integer accommodationId = 999;
		when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.ACCOMMODATION_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.findAccommodation(accommodationId));
	}
	
	@Test
	public void Should_save_the_given_accommodation_entity() {
		
		//Arrange
		Accommodation accommodation = new Accommodation();
		accommodation.setId(1);
		accommodation.setAccommodationType("Hostel");
		
		ArgumentCaptor<Accommodation> captor = ArgumentCaptor.forClass(Accommodation.class);

		//Act
		sut.saveAccommodation(accommodation);
		
		//Assert
		verify(accommodationRepository).save(captor.capture());
		assertThat(accommodation).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_delete_a_course_entity_by_the_given_id() {
		
		//Arrange
		Accommodation accommodation = new Accommodation();
		Integer accommodationId = 1;
		accommodation.setId(accommodationId);
		
		when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));
		
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		
		//Act
		sut.deleteAccommodation(accommodationId);
		
		//Assert
		verify(accommodationRepository).deleteById(captor.capture());
		assertThat(accommodationId).isEqualTo(captor.getValue());
	}
}
