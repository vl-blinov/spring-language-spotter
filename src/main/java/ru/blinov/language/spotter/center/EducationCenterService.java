package ru.blinov.language.spotter.center;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityRepository;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.country.CountryRepository;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseRepository;
import ru.blinov.language.spotter.enums.Entity;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;
import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class EducationCenterService {
	
	private LanguageRepository languageRepository;
	
	private CountryRepository countryRepository;
	
	private CityRepository cityRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	private CourseRepository courseRepository;
	
	private RequestValidator requestValidator;

	@Autowired
	public EducationCenterService(LanguageRepository languageRepository, CountryRepository countryRepository,
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
	public List<EducationCenter> findAllEducationCenters(String languageName, String countryName, String cityName) {
		
		requestValidator.checkLanguageAndCountryAndCity(languageName, countryName, cityName);
		
		return educationCenterRepository.findAllByLanguageNameAndCityName(languageName, cityName);
	}

	@Transactional(readOnly = true)
	public EducationCenter findEducationCenter(String languageName, String countryName, String cityName, String centerName) {
		
		Map<Entity, Object> entities = requestValidator.checkLanguageAndCountryAndCityAndCenter(languageName, countryName, cityName, centerName);
		
		EducationCenter center = (EducationCenter) entities.get(Entity.EDUCATION_CENTER);
		
		return center;
	}
	
	@Transactional
	public void saveEducationCenter(EducationCenter center) {
		educationCenterRepository.save(center);
	}
	
	@Transactional
	public void deleteEducationCenter(String languageName, String countryName, String cityName, String centerName) {
		
		Map<Entity, Object> entities = requestValidator.checkLanguageAndCountryAndCityAndCenter(languageName, countryName, cityName, centerName);
		
		Language language = (Language) entities.get(Entity.LANGUAGE);
		
		Country country = (Country) entities.get(Entity.COUNTRY);
		
		City city = (City) entities.get(Entity.CITY);
		
		EducationCenter center = (EducationCenter) entities.get(Entity.EDUCATION_CENTER);
		
		//>>>3
		
		List<Language> centerLanguages = center.getLanguages();
		
		if(centerLanguages.size() == 1 && centerLanguages.contains(language)) {
			
			educationCenterRepository.delete(center);
			
			return;
		}
		
		centerLanguages.removeIf(l -> l.getName().equals(languageName));
		
		educationCenterRepository.save(center);
		
		//>>>4
		
		List<Course> courses = courseRepository.findAllByLanguageNameAndCenterName(languageName, centerName);
		
		courses.forEach(c -> {
			if(c.getLanguage().getName().equals(languageName)) {
				courseRepository.delete(c);
			}
		});
		
		courses.removeIf(c -> c.getLanguage().getName().equals(languageName));
		
		courseRepository.saveAll(courses);
	}
}
