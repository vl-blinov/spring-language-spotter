package ru.blinov.language.spotter.city;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.center.EducationCenterService;
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
	public List<City> findAllCities(String languageName, String countryName) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName);

		return cityRepository.findAllByLanguageNameAndCountryName(StringFormatter.formatPathVariable(languageName),
				StringFormatter.formatPathVariable(countryName));
	}

	@Transactional(readOnly = true)
	public City findCity(Integer cityId) {
		
		Optional<City> cityOptional = cityRepository.findById(cityId);
		
		if(cityOptional.isEmpty()) {
			throw new RequestUrlException("City with id '" + cityId + "' is not found");
		}
		
		City city = cityOptional.get();
		
		return city;
	}

	@Transactional
	public void addCity(City city) {
		cityRepository.save(city);
	}
	
	@Transactional
	public void addLanguageToCity(Integer cityId, Integer languageId) {
		
		Language language = languageService.findLanguage(languageId);
		
		City city = findCity(cityId);
		
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
		
		City city = findCity(cityId);
		
		cityRepository.delete(city);
	}
	
	@Transactional
	public void removeLanguageFromCity(Integer cityId, Integer languageId) {
		
		Language language = languageService.findLanguage(languageId);
		
		City city = findCity(cityId);
		
		city.removeLanguage(language);
	}
	
	@Transactional
	public void removeEducationCenterFromCity(Integer cityId, Integer centerId) {
		
		City city = findCity(cityId);
		
		EducationCenter center = educationCenterService.findEducationCenter(centerId);

		city.removeEducationCenter(center);
	}
}
