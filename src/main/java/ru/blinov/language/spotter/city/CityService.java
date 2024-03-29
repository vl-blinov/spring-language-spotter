package ru.blinov.language.spotter.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.center.EducationCenterService;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageService;
import ru.blinov.language.spotter.util.StringFormatter;
import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class CityService {
	
	private LanguageService languageService;
	
	private EducationCenterService educationCenterService;
	
	private CityRepository cityRepository;
	
	private RequestValidator requestValidator;

	@Autowired
	public CityService(LanguageService languageService, EducationCenterService educationCenterService,
			CityRepository cityRepository, RequestValidator requestValidator) {
		this.languageService = languageService;
		this.educationCenterService = educationCenterService;
		this.cityRepository = cityRepository;
		this.requestValidator = requestValidator;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCities() {
		return cityRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCities(String languageNamePathVariable, String countryNamePathVariable) {
		
		requestValidator.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable);
		
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		String countryName = StringFormatter.formatPathVariable(countryNamePathVariable);
		
		return cityRepository.findAllByLanguageNameAndCountryName(languageName, countryName);
	}

	@Transactional(readOnly = true)
	public City findCity(Integer cityId) {
		
		City city = cityRepository.findById(cityId)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.CITY_NOT_FOUND.getMessage()));
		
		return city;
	}

	@Transactional
	public void addCity(City city) {
		cityRepository.save(city);
	}
	
	@Transactional
	public void addLanguageToCity(Integer cityId, Integer languageId) {
		
		City city = findCity(cityId);
		
		Language language = languageService.findLanguage(languageId);

		city.addLanguage(language);
	}
	
	@Transactional
	public void addEducationCenterToCity(Integer cityId, Integer centerId) {
		
		City city = findCity(cityId);
		
		EducationCenter center = educationCenterService.findEducationCenter(centerId);

		city.addEducationCenter(center);
	}
	
	@Transactional
	public void deleteCity(Integer cityId) {
		
		findCity(cityId);
		
		cityRepository.deleteById(cityId);
	}
	
	@Transactional
	public void removeLanguageFromCity(Integer cityId, Integer languageId) {
		
		City city = findCity(cityId);
		
		Language language = languageService.findLanguage(languageId);

		city.removeLanguage(language);
	}
	
	@Transactional
	public void removeEducationCenterFromCity(Integer cityId, Integer centerId) {
		
		City city = findCity(cityId);
		
		EducationCenter center = educationCenterService.findEducationCenter(centerId);

		city.removeEducationCenter(center);
	}
}
