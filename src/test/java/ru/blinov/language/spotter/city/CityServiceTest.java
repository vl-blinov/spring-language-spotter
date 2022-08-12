package ru.blinov.language.spotter.city;

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
import ru.blinov.language.spotter.center.EducationCenterService;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageService;
import ru.blinov.language.spotter.util.StringFormatter;
import ru.blinov.language.spotter.validator.RequestValidator;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {
	
	@Mock
	private LanguageService languageService;
	
	@Mock
	private EducationCenterService educationCenterService;
	
	@Mock
	private CityRepository cityRepository;
	
	@Mock
	private RequestValidator requestValidator;
	
	@InjectMocks
	private CityService sut;
	
	@Test
	public void Should_get_all_city_entities() {
		
		//Arrange
		List<City> cities = Lists.newArrayList(new City(), new City());
		
		when(cityRepository.findAll()).thenReturn(cities);
		
		int expectedNumberOfCities = cities.size();
		
		//Act
		List<City> result = sut.findAllCities();
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCities);		
	}
	
	@Test
	public void Should_get_all_city_entities_by_language_name_and_country_name() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		
		Language language = new Language();
		language.setName("English");	
		Country country = new Country();
		country.setName("Ireland");
		
		City cityOne = new City();
		cityOne.setLanguages(Lists.newArrayList(language));
		cityOne.setCountry(country);
		City cityTwo = new City();
		cityTwo.setLanguages(Lists.newArrayList(language));
		cityTwo.setCountry(country);
		
		List<City> cities = Lists.newArrayList(cityOne, cityTwo);
	
		when(cityRepository.findAllByLanguageNameAndCountryName(languageName, countryName)).thenReturn(cities);
		
		int expectedNumberOfCities = cities.size();
		
		//Act
		List<City> result = sut.findAllCities(languageNamePathVariable, countryNamePathVariable);
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCities);
		assertThat(result).allMatch(c -> c.hasLanguage(languageName) && c.getCountry().getName().equals(countryName));
	}
	
	@Test
	public void Should_get_a_city_entity_by_the_given_id() {
		
		//Arrange
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		
		when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
		
		int expectedCityId = cityId;

		//Act
		City result = sut.findCity(cityId);
		
		//Assert
		assertThat(result.getId()).isEqualTo(expectedCityId);
	}
	
	@Test
	public void When_trying_to_get_a_city_entity_by_the_given_id_then_should_throw_requestUrlException_CITY_NOT_FOUND() {
		
		//Arrange
		Integer cityId = 999;
		when(cityRepository.findById(cityId)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.CITY_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.findCity(cityId));
	}
	
	@Test
	public void Should_add_the_given_city_entity() {
		
		//Arrange
		City city = new City();
		city.setName("Dublin");
		
		ArgumentCaptor<City> captor = ArgumentCaptor.forClass(City.class);

		//Act
		sut.addCity(city);
		
		//Assert
		verify(cityRepository).save(captor.capture());
		assertThat(city).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_add_language_to_city() {
		
		//Arrange
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		city.setLanguages(Lists.newArrayList());
		when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
		
		Language language = new Language();
		Integer languageId = 1;
		language.setId(languageId);
		when(languageService.findLanguage(languageId)).thenReturn(language);

		//Act
		sut.addLanguageToCity(cityId, languageId);
			
		//Assert
		assertThat(city.getLanguages()).anyMatch(l -> l.getId() == languageId);
	}
	
	@Test
	public void Should_add_education_center_to_city() {
		
		//Arrange
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		city.setEducationCenters(Lists.newArrayList());
		when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
		
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		when(educationCenterService.findEducationCenter(centerId)).thenReturn(center);

		//Act
		sut.addEducationCenterToCity(cityId, centerId);
			
		//Assert
		assertThat(city.getEducationCenters()).anyMatch(c -> c.getId() == centerId);
	}
	
	@Test
	public void Should_delete_a_city_entity_by_the_given_id() {
		
		//Arrange
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		
		when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
		
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		
		//Act
		sut.deleteCity(cityId);
		
		//Assert
		verify(cityRepository).deleteById(captor.capture());
		assertThat(cityId).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_remove_language_from_city() {
		
		//Arrange
		Language languageOne = new Language();
		Integer languageOneId = 1;
		languageOne.setId(languageOneId);
		
		Language languageTwo = new Language();
		Integer languageTwoId = 2;
		languageTwo.setId(languageTwoId);
		
		when(languageService.findLanguage(languageOneId)).thenReturn(languageOne);
		
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		city.setLanguages(Lists.newArrayList(languageOne, languageTwo));
		when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

		//Act
		sut.removeLanguageFromCity(cityId, languageOneId);
			
		//Assert
		assertThat(city.getLanguages()).anyMatch(l -> l.getId() != languageOneId);
		assertThat(city.getLanguages()).anyMatch(l -> l.getId() == languageTwoId);
	}
	
	@Test
	public void Should_remove_education_center_from_city() {
		
		//Arrange
		EducationCenter centerOne = new EducationCenter();
		Integer centerOneId = 1;
		centerOne.setId(centerOneId);
		
		EducationCenter centerTwo = new EducationCenter();
		Integer centerTwoId = 2;
		centerTwo.setId(centerTwoId);
		
		when(educationCenterService.findEducationCenter(centerOneId)).thenReturn(centerOne);
		
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		city.setEducationCenters(Lists.newArrayList(centerOne, centerTwo));
		when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

		//Act
		sut.removeEducationCenterFromCity(cityId, centerOneId);
			
		//Assert
		assertThat(city.getEducationCenters()).anyMatch(c -> c.getId() != centerOneId);
		assertThat(city.getEducationCenters()).anyMatch(c -> c.getId() == centerTwoId);
	}
}
