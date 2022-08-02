package ru.blinov.language.spotter.country;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityService;
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
	public List<Country> findAllCountries(String languageName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		requestValidator.checkUrlPathVariables(languageName);
		
		return countryRepository.findAllByLanguageName(languageName);
	}

	@Transactional(readOnly = true)
	public Country findCountry(Integer countryId) {
		
		Optional<Country> countryOptional = countryRepository.findById(countryId);
		
		if(countryOptional.isEmpty()) {
			throw new RequestUrlException("Country with id '" + countryId + "' is not found");
		}
		
		Country country = countryOptional.get();
		
		return country;
	}
	
	@Transactional
	public void addCountry(Country country) {
		countryRepository.save(country);
	}
	
	@Transactional
	public void addLanguageToCountry(Integer countryId, Integer languageId) {

		Language language = languageService.findLanguage(languageId);
		
		Country country = findCountry(countryId);
		
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
		
		Country country = findCountry(countryId);
		
		countryRepository.delete(country);
	}

	@Transactional
	public void removeLanguageFromCountry(Integer countryId, Integer languageId) {
		
		Language language = languageService.findLanguage(languageId);
		
		Country country = findCountry(countryId);
		
		country.removeLanguage(language);
	}
	
	@Transactional
	public void removeCityFromCountry(Integer countryId, Integer cityId) {
		
		Country country = findCountry(countryId);
		
		City city = cityService.findCity(cityId);
		
		country.removeCity(city);	
	}
}
