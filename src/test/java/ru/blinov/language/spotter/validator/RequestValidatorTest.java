package ru.blinov.language.spotter.validator;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.center.EducationCenterRepository;
import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityRepository;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.country.CountryRepository;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;
import ru.blinov.language.spotter.util.StringFormatter;

@ExtendWith(MockitoExtension.class)
public class RequestValidatorTest {
	
	@Mock
	private LanguageRepository languageRepository;
	
	@Mock
	private CountryRepository countryRepository;
	
	@Mock
	private CityRepository cityRepository;
	
	@Mock
	private EducationCenterRepository educationCenterRepository;
	
	@InjectMocks
	private RequestValidator sut;
	
	@Test
	public void While_checking_language_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));

		//Assert
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable));
	}
	
	@Test
	public void While_checking_language_should_throw_requestUrlException_LANGUAGE_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "englishh";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);	
		when(languageRepository.findByName(languageName)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.LANGUAGE_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);	
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));

		//Assert
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable));
	}
	
	@Test
	public void While_checking_language_and_country_should_throw_requestUrlException_COUNTRY_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "irelandd";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		when(countryRepository.findByName(countryName)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.COUNTRY_NOT_FOUND.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable));
	}	
	
	@Test
	@Transactional
	public void While_checking_language_and_country_should_throw_requestUrlException_COUNTRY_LANGUAGE_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "french";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		Language languageOfCountry = new Language();
		languageOfCountry.setName("English");
		country.setLanguages(List.of(languageOfCountry));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String message = RequestUrlMessage.COUNTRY_LANGUAGE_DISCR.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		City city = new City();
		city.setName(cityName);
		city.setCountry(country);
		city.setLanguages(List.of(language));
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));

		//Assert
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_throw_requestUrlException_CITY_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "dublinn";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		when(cityRepository.findByName(cityName)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.CITY_NOT_FOUND.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_throw_requestUrlException_CITY_COUNTRY_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "canada";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		City city = new City();
		city.setName(cityName);
		Country countryOfCity = new Country();
		countryOfCity.setName("Ireland");
		city.setCountry(countryOfCity);
		city.setLanguages(List.of(language));
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		
		String message = RequestUrlMessage.CITY_COUNTRY_DISCR.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_should_throw_requestUrlException_CITY_LANGUAGE_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "irish";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "cork";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		City city = new City();
		city.setName(cityName);
		city.setCountry(country);
		Language languageOfCity = new Language();
		languageOfCity.setName("English");
		city.setLanguages(List.of(languageOfCity));
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		
		String message = RequestUrlMessage.CITY_LANGUAGE_DISCR.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_not_throw_any_exception() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		City city = new City();
		city.setName(cityName);
		city.setCountry(country);
		city.setLanguages(List.of(language));
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		
		String centerNamePathVariable = "erin_school_of_english";
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		EducationCenter center = new EducationCenter();
		center.setName(centerName);
		center.setCity(city);
		center.setLanguages(List.of(language));
		when(educationCenterRepository.findByName(centerName)).thenReturn(Optional.of(center));

		//Assert
		assertDoesNotThrow(
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_throw_requestUrlException_EDUCATION_CENTER_NOT_FOUND() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		City city = new City();
		city.setName(cityName);
		city.setCountry(country);
		city.setLanguages(List.of(language));
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		
		String centerNamePathVariable = "erin_school_of_englishh";
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		when(educationCenterRepository.findByName(centerName)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_throw_requestUrlException_EDUCATION_CENTER_CITY_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		City city = new City();
		city.setName(cityName);
		city.setCountry(country);
		city.setLanguages(List.of(language));
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		
		String centerNamePathVariable = "cork_english_college";
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		EducationCenter center = new EducationCenter();
		center.setName(centerName);
		City cityOfCenter = new City();
		cityOfCenter.setName("Cork");
		center.setCity(cityOfCenter);
		center.setLanguages(List.of(language));
		when(educationCenterRepository.findByName(centerName)).thenReturn(Optional.of(center));
		
		String message = RequestUrlMessage.EDUCATION_CENTER_CITY_DISCR.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}
	
	@Test
	@Transactional
	public void While_checking_language_and_country_and_city_and_center_should_throw_requestUrlException_EDUCATION_CENTER_LANGUAGE_DISCR() {
		
		//Arrange
		String languageNamePathVariable = "irish";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		Language language = new Language();
		language.setName(languageName);
		when(languageRepository.findByName(languageName)).thenReturn(Optional.of(language));
		
		String countryNamePathVariable = "ireland";
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		Country country = new Country();
		country.setName(countryName);
		country.setLanguages(List.of(language));
		when(countryRepository.findByName(countryName)).thenReturn(Optional.of(country));
		
		String cityNamePathVariable = "dublin";
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		City city = new City();
		city.setName(cityName);
		city.setCountry(country);
		city.setLanguages(List.of(language));
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		
		String centerNamePathVariable = "erin_school_of_english";
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		EducationCenter center = new EducationCenter();
		center.setName(centerName);
		center.setCity(city);
		Language languageOfCenter = new Language();
		languageOfCenter.setName("English");
		center.setLanguages(List.of(languageOfCenter));
		when(educationCenterRepository.findByName(centerName)).thenReturn(Optional.of(center));
		
		String message = RequestUrlMessage.EDUCATION_CENTER_LANGUAGE_DISCR.getMessage();

		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable));
	}	
}
