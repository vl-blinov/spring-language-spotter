package ru.blinov.language.spotter.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityService;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageService;
import ru.blinov.language.spotter.util.StringFormatter;
import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class CountryService {
	
	private LanguageService languageService;
	
	private CityService cityService;
	
	private CountryRepository countryRepository;
	
	private RequestValidator requestValidator;
	
	@Autowired
	public CountryService(LanguageService languageService, CityService cityService, CountryRepository countryRepository,
			RequestValidator requestValidator) {
		this.languageService = languageService;
		this.cityService = cityService;
		this.countryRepository = countryRepository;
		this.requestValidator = requestValidator;
	}
	
	@Transactional(readOnly = true)
	public List<Country> findAllCountries() {
		return countryRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Country> findAllCountries(String languageNamePathVariable) {

		requestValidator.checkUrlPathVariables(languageNamePathVariable);
		
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		return countryRepository.findAllByLanguageName(languageName);
	}

	@Transactional(readOnly = true)
	public Country findCountry(Integer countryId) {
		
		Country country = countryRepository.findById(countryId)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.COUNTRY_NOT_FOUND.getMessage()));
		
		return country;
	}
	
	@Transactional
	public void addCountry(Country country) {
		countryRepository.save(country);
	}
	
	@Transactional
	public void addLanguageToCountry(Integer countryId, Integer languageId) {

		Country country = findCountry(countryId);
		
		Language language = languageService.findLanguage(languageId);
		
		country.addLanguage(language);
	}
	
	@Transactional
	public void addCityToCountry(Integer countryId, Integer cityId) {
		
		Country country = findCountry(countryId);
		
		City city = cityService.findCity(cityId);
		
		country.addCity(city);
	}
	
	@Transactional
	public void deleteCountry(Integer countryId) {
		
		findCountry(countryId);
		
		countryRepository.deleteById(countryId);
	}

	@Transactional
	public void removeLanguageFromCountry(Integer countryId, Integer languageId) {
		
		Country country = findCountry(countryId);
		
		Language language = languageService.findLanguage(languageId);
		
		country.removeLanguage(language);
	}
	
	@Transactional
	public void removeCityFromCountry(Integer countryId, Integer cityId) {
		
		Country country = findCountry(countryId);
		
		City city = cityService.findCity(cityId);
		
		country.removeCity(city);	
	}
}
