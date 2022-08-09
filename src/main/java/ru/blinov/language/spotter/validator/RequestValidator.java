package ru.blinov.language.spotter.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		
		Optional<Language> languageOptional = languageRepository.findByName(languageName);
		
		if(languageOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.LANGUAGE_NOT_FOUND.getMessage());
		}
	}

	public void checkUrlPathVariables(String languageName, String countryName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		checkUrlPathVariables(languageName);
		
		Optional<Country> countryOptional = countryRepository.findByName(countryName);
		
		if(countryOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.COUNTRY_NOT_FOUND.getMessage());
		}
		
		Country country = countryOptional.get();
		
		if(!country.hasLanguage(languageName)) {
			throw new RequestUrlException(RequestUrlMessage.COUNTRY_LANGUAGE_DISCR.getMessage());
		}
	}
	
	public void checkUrlPathVariables(String languageName, String countryName, String cityName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		checkUrlPathVariables(languageName, countryName);
		
		Optional<City> cityOptional = cityRepository.findByName(cityName);
		
		if(cityOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.CITY_NOT_FOUND.getMessage());
		}
		
		City city = cityOptional.get();
		
		if(!city.getCountry().getName().equals(countryName)) {
			throw new RequestUrlException(RequestUrlMessage.CITY_COUNTRY_DISCR.getMessage());
		}
		
		if(!city.hasLanguage(languageName)) {
			throw new RequestUrlException(RequestUrlMessage.CITY_LANGUAGE_DISCR.getMessage());
		}	
	}
	
	public void checkUrlPathVariables(String languageName, String countryName, String cityName, String centerName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		checkUrlPathVariables(languageName, countryName, cityName);
		
		Optional<EducationCenter> centerOptional = educationCenterRepository.findByName(centerName);
		
		if(centerOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage());
		}
		
		EducationCenter center = centerOptional.get();
		
		if(!center.getCity().getName().equals(cityName)) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_CITY_DISCR.getMessage());
		}
		
		if(!center.hasLanguage(languageName)) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_LANGUAGE_DISCR.getMessage());
		}
	}
}
