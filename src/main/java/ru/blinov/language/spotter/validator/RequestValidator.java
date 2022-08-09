package ru.blinov.language.spotter.validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.center.EducationCenterRepository;
import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityRepository;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.country.CountryRepository;
import ru.blinov.language.spotter.enums.Entity;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;
import ru.blinov.language.spotter.util.StringFormatter;

@Component
public class RequestValidator {
	
	private LanguageRepository languageRepository;
	
	private CountryRepository countryRepository;
	
	private CityRepository cityRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	@Autowired
	public RequestValidator(LanguageRepository languageRepository, CountryRepository countryRepository,
			CityRepository cityRepository, EducationCenterRepository educationCenterRepository) {
		this.languageRepository = languageRepository;
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
		this.educationCenterRepository = educationCenterRepository;
	}

	public void checkUrlPathVariables(String languageName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		checkLanguage(languageName);
	}

	public void checkUrlPathVariables(String languageName, String countryName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		checkLanguageAndCountry(languageName, countryName);
	}
	
	public void checkUrlPathVariables(String languageName, String countryName, String cityName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		checkLanguageAndCountryAndCity(languageName, countryName, cityName);
	}
	
	public void checkUrlPathVariables(String languageName, String countryName, String cityName, String centerName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		checkLanguageAndCountryAndCityAndCenter(languageName, countryName, cityName, centerName);
	}

	private Map<Entity, Object> checkLanguage(String languageName) {
		
		Optional<Language> languageOptional = languageRepository.findByName(languageName);
		
		if(languageOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.LANGUAGE_NOT_FOUND.getMessage());
		}
		
		Language language = languageOptional.get();
		
		return Map.of(Entity.LANGUAGE, language);
	}
	
	private Map<Entity, Object> checkLanguageAndCountry(String languageName, String countryName) {
		
		Language language = (Language) checkLanguage(languageName).get(Entity.LANGUAGE);
		
		Optional<Country> countryOptional = countryRepository.findByName(countryName);
		
		if(countryOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.COUNTRY_NOT_FOUND.getMessage());
		}
		
		Country country = countryOptional.get();
		
		List<Country> countries = language.getCountries();
		
		if(!countries.contains(country)) {
			throw new RequestUrlException(RequestUrlMessage.COUNTRY_LANGUAGE_DISCR.getMessage());
		}
		
		return Map.of(Entity.LANGUAGE, language, Entity.COUNTRY, country);
	}
	
	private Map<Entity, Object> checkLanguageAndCountryAndCity(String languageName, String countryName, String cityName) {
		
		Map<Entity, Object> entities = checkLanguageAndCountry(languageName, countryName);
		
		Language language = (Language) entities.get(Entity.LANGUAGE);
		
		Country country = (Country) entities.get(Entity.COUNTRY);
		
		Optional<City> cityOptional = cityRepository.findByName(cityName);
		
		if(cityOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.CITY_NOT_FOUND.getMessage());
		}
		
		City city = cityOptional.get();
		
		List<City> countryCities = country.getCities();
		
		if(!countryCities.contains(city)) {
			throw new RequestUrlException(RequestUrlMessage.CITY_COUNTRY_DISCR.getMessage());
		}
		
		List<City> languageCities = language.getCities();
		
		if(!languageCities.contains(city)) {
			throw new RequestUrlException(RequestUrlMessage.CITY_LANGUAGE_DISCR.getMessage());
		}
		
		return Map.of(Entity.LANGUAGE, language, Entity.COUNTRY, country, Entity.CITY, city);
	}
	
	private Map<Entity, Object> checkLanguageAndCountryAndCityAndCenter(String languageName, String countryName, String cityName, String centerName) {
		
		Map<Entity, Object> entities = checkLanguageAndCountryAndCity(languageName, countryName, cityName);
		
		Language language = (Language) entities.get(Entity.LANGUAGE);
		
		Country country = (Country) entities.get(Entity.COUNTRY);
		
		City city = (City) entities.get(Entity.CITY);
		
		Optional<EducationCenter> centerOptional = educationCenterRepository.findByName(centerName);
		
		if(centerOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage());
		}
		
		EducationCenter center = centerOptional.get();
		
		List<EducationCenter> cityCenters = city.getEducationCenters();
		
		if(!cityCenters.contains(center)) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_CITY_DISCR.getMessage());
		}
		
		List<EducationCenter> languageCenters = language.getEducationCenters();
		
		if(!languageCenters.contains(center)) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_LANGUAGE_DISCR.getMessage());
		}

		return Map.of(Entity.LANGUAGE, language, Entity.COUNTRY, country, Entity.CITY, city, Entity.EDUCATION_CENTER, center);
	}
}
