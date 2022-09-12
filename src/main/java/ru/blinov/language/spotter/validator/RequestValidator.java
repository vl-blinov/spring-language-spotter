package ru.blinov.language.spotter.validator;

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

	public void checkUrlPathVariables(String languageNamePathVariable) {
		
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		languageRepository.findByName(languageName)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.LANGUAGE_NOT_FOUND.getMessage()));

	}

	public void checkUrlPathVariables(String languageNamePathVariable, String countryNamePathVariable) {
		
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		
		checkUrlPathVariables(languageNamePathVariable);
		
		Country country = countryRepository.findByName(countryName)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.COUNTRY_NOT_FOUND.getMessage()));
		
		if(!country.hasLanguage(languageName)) {
			throw new RequestUrlException(RequestUrlMessage.COUNTRY_LANGUAGE_DISCR.getMessage());
		}
	}
	
	public void checkUrlPathVariables(String languageNamePathVariable, String countryNamePathVariable, String cityNamePathVariable) {
		
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		
		checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable);
		
		City city = cityRepository.findByName(cityName)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.CITY_NOT_FOUND.getMessage()));
		
		if(!city.getCountry().getName().equals(countryName)) {
			throw new RequestUrlException(RequestUrlMessage.CITY_COUNTRY_DISCR.getMessage());
		}
		
		if(!city.hasLanguage(languageName)) {
			throw new RequestUrlException(RequestUrlMessage.CITY_LANGUAGE_DISCR.getMessage());
		}	
	}
	
	public void checkUrlPathVariables(String languageNamePathVariable, String countryNamePathVariable, String cityNamePathVariable,
			String centerNamePathVariable) {
		
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		
		checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable);
		
		EducationCenter center = educationCenterRepository.findByName(centerName)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage()));
		
		if(!center.getCity().getName().equals(cityName)) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_CITY_DISCR.getMessage());
		}
		
		if(!center.hasLanguage(languageName)) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_LANGUAGE_DISCR.getMessage());
		}
	}
}
