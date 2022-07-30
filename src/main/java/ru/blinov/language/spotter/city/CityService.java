package ru.blinov.language.spotter.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.center.EducationCenterRepository;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.country.CountryRepository;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseRepository;
import ru.blinov.language.spotter.enums.Entity;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;
import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class CityService {
	
	private LanguageRepository languageRepository;
	
	private CountryRepository countryRepository;
	
	private CityRepository cityRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	private CourseRepository courseRepository;
	
	private RequestValidator requestValidator;
	
	@Autowired
	public CityService(LanguageRepository languageRepository, CountryRepository countryRepository,
					   CityRepository cityRepository, EducationCenterRepository educationCenterRepository,
					   CourseRepository courseRepository, RequestValidator requestValidator) {
		
		this.languageRepository = languageRepository;
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
		this.educationCenterRepository = educationCenterRepository;
		this.courseRepository = courseRepository;
		this.requestValidator = requestValidator;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCities(String countryName, String languageName) {
		
		requestValidator.checkLanguageAndCountry(languageName, countryName);
		
		return cityRepository.findAllByLanguageNameAndCountryName(languageName, countryName);
	}

	@Transactional
	public void saveCity(City city) {
		cityRepository.save(city);
	}
	
	@Transactional
	public void deleteCity(String languageName, String countryName, String cityName) {
		
		Map<Entity, Object> entities = requestValidator.checkLanguageAndCountryAndCity(languageName, countryName, cityName);
		
		Language language = (Language) entities.get(Entity.LANGUAGE);
		
		Country country = (Country) entities.get(Entity.COUNTRY);
		
		City city = (City) entities.get(Entity.CITY);
		
		//>>>3
		
		List<Language> cityLanguages = city.getLanguages();
		
		if(cityLanguages.size() == 1 && cityLanguages.contains(language)) {
			
			cityRepository.delete(city);
			
			return;
		}
		
		cityLanguages.removeIf(l -> l.getName().equals(languageName));
		
		cityRepository.save(city);
		
		//>>>4
		
		List<EducationCenter> centers = educationCenterRepository.findAllByLanguageNameAndCityName(languageName, cityName);
		
		centers.forEach(c -> {
			
			List<Language> centerLanguages = c.getLanguages();
			
			if(centerLanguages.size() == 1 && centerLanguages.contains(language)) {
				educationCenterRepository.delete(c);
			} else {
				centerLanguages.removeIf(l -> l.getName().equals(languageName));
			}
		});
		
		centers.removeIf(c -> {
			
			List<Language> centerLanguages = c.getLanguages();
			
			return centerLanguages.size() == 1 && centerLanguages.contains(language);
		});
		
		educationCenterRepository.saveAll(centers);
		
		//>>>5
		
		List<String> centersNames = new ArrayList<>();
		
		centers.forEach(c -> {
			centersNames.add(c.getName());
		});
		
		List<Course> courses = courseRepository.findAllByLanguageNameAndCentersNames(languageName, centersNames);
		
		courses.forEach(c -> {
			if(c.getLanguage().getName().equals(languageName)) {
				courseRepository.delete(c);
			}
		});
		
		courses.removeIf(c -> c.getLanguage().getName().equals(languageName));
		
		courseRepository.saveAll(courses);
	}
}
