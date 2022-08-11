package ru.blinov.language.spotter.country;

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

import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityService;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageService;
import ru.blinov.language.spotter.util.StringFormatter;
import ru.blinov.language.spotter.validator.RequestValidator;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {
	
	@Mock
	private LanguageService languageService;
	
	@Mock
	private CityService cityService;
	
	@Mock
	private CountryRepository countryRepository;
	
	@Mock
	private RequestValidator requestValidator;
	
	@InjectMocks
	private CountryService sut;
	
	@Test
	public void Should_get_all_country_entities() {
		
		//Arrange
		List<Country> countries = Lists.newArrayList(new Country(), new Country());
		
		when(countryRepository.findAll()).thenReturn(countries);
		
		int expectedNumberOfCountries = countries.size();
		
		//Act
		List<Country> result = sut.findAllCountries();
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCountries);		
	}
	
	@Test
	public void Should_get_all_country_entities_by_language_name() {
		
		//Arrange
		String languageNamePathVariable = "english";
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		Language language = new Language();
		language.setName("English");
		
		Country countryOne = new Country();
		countryOne.setLanguages(Lists.newArrayList(language));
		Country countryTwo = new Country();
		countryTwo.setLanguages(Lists.newArrayList(language));
		
		List<Country> countries = Lists.newArrayList(countryOne, countryTwo);
	
		when(countryRepository.findAllByLanguageName(languageName)).thenReturn(countries);
		
		int expectedNumberOfCountries = countries.size();
		
		//Act
		List<Country> result = sut.findAllCountries(languageNamePathVariable);
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfCountries);
		assertThat(result).allMatch(c -> c.hasLanguage(languageName));
	}
	
	@Test
	public void Should_get_a_country_entity_by_the_given_id() {
		
		//Arrange
		Country country = new Country();
		Integer countryId = 1;
		country.setId(countryId);
		
		when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
		
		int expectedCountryId = countryId;

		//Act
		Country result = sut.findCountry(countryId);
		
		//Assert
		assertThat(result.getId()).isEqualTo(expectedCountryId);
	}
	
	@Test
	public void When_trying_to_get_a_country_entity_by_the_given_id_then_should_throw_requestUrlException_COUNTRY_NOT_FOUND() {
		
		//Arrange
		Integer countryId = 999;
		when(countryRepository.findById(countryId)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.COUNTRY_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.findCountry(countryId));
	}
	
	@Test
	public void Should_add_the_given_country_entity() {
		
		//Arrange
		Country country = new Country();
		country.setName("Ireland");
		
		ArgumentCaptor<Country> captor = ArgumentCaptor.forClass(Country.class);

		//Act
		sut.addCountry(country);
		
		//Assert
		verify(countryRepository).save(captor.capture());
		assertThat(country).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_add_language_to_country() {
		
		//Arrange
		Country country = new Country();
		Integer countryId = 1;
		country.setId(countryId);
		country.setLanguages(Lists.newArrayList());
		when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
		
		Language language = new Language();
		Integer languageId = 1;
		language.setId(languageId);
		when(languageService.findLanguage(languageId)).thenReturn(language);

		//Act
		sut.addLanguageToCountry(countryId, languageId);
			
		//Assert
		assertThat(country.getLanguages()).anyMatch(l -> l.getId() == languageId);
	}
	
	@Test
	public void Should_add_city_to_country() {
		
		//Arrange
		Country country = new Country();
		Integer countryId = 1;
		country.setId(countryId);
		country.setCities(Lists.newArrayList());
		when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
		
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		when(cityService.findCity(cityId)).thenReturn(city);

		//Act
		sut.addCityToCountry(countryId, cityId);
			
		//Assert
		assertThat(country.getCities()).anyMatch(c -> c.getId() == cityId);
	}

	@Test
	public void Should_delete_a_language_entity_by_the_given_id() {
		
		//Arrange
		Country country = new Country();
		Integer countryId = 1;
		country.setId(countryId);
		
		when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
		
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		
		//Act
		sut.deleteCountry(countryId);
		
		//Assert
		verify(countryRepository).deleteById(captor.capture());
		assertThat(countryId).isEqualTo(captor.getValue());
	}
	
	@Test
	public void Should_remove_language_from_country() {
		
		//Arrange
		Language languageOne = new Language();
		Integer languageOneId = 1;
		languageOne.setId(languageOneId);
		
		Language languageTwo = new Language();
		Integer languageTwoId = 2;
		languageTwo.setId(languageTwoId);
		
		when(languageService.findLanguage(languageOneId)).thenReturn(languageOne);
		
		Country country = new Country();
		Integer countryId = 1;
		country.setId(countryId);
		country.setLanguages(Lists.newArrayList(languageOne, languageTwo));
		when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

		//Act
		sut.removeLanguageFromCountry(countryId, languageOneId);
			
		//Assert
		assertThat(country.getLanguages()).anyMatch(l -> l.getId() != languageOneId);
		assertThat(country.getLanguages()).anyMatch(l -> l.getId() == languageTwoId);
	}
	
	@Test
	public void Should_remove_city_from_country() {
		
		//Arrange
		City cityOne = new City();
		Integer cityOneId = 1;
		cityOne.setId(cityOneId);
		
		City cityTwo = new City();
		Integer cityTwoId = 2;
		cityTwo.setId(cityTwoId);
		
		when(cityService.findCity(cityOneId)).thenReturn(cityOne);
		
		Country country = new Country();
		Integer countryId = 1;
		country.setId(countryId);
		country.setCities(Lists.newArrayList(cityOne, cityTwo));
		when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

		//Act
		sut.removeCityFromCountry(countryId, cityOneId);
			
		//Assert
		assertThat(country.getCities()).anyMatch(c -> c.getId() != cityOneId);
		assertThat(country.getCities()).anyMatch(c -> c.getId() == cityTwoId);
	}
}
