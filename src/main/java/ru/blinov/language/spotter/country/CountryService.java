package ru.blinov.language.spotter.country;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.center.EducationCenterRepository;
import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityRepository;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseRepository;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;
import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class CountryService {
	
	private LanguageRepository languageRepository;
	
	private CountryRepository countryRepository;
	
	private CityRepository cityRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	private CourseRepository courseRepository;
	
	private RequestValidator requestValidator;
	
	@Autowired
	public CountryService(LanguageRepository languageRepository, CountryRepository countryRepository,
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
	public List<Country> findAllCountries(String languageName) {
		
		requestValidator.checkUrlPathVariables(languageName);
		
		return countryRepository.findAllByLanguageName(languageName);
	}

	@Transactional
	public void saveCountry(Country country) {
		countryRepository.save(country);
	}
	
	@Transactional
	public void deleteCountry(String languageName, String countryName) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName);
		
		Language language = languageRepository.findByName(languageName).get();
		
		Country country = countryRepository.findByName(countryName).get();
		
		List<Language> countryLanguages = country.getLanguages();
		
		if(countryLanguages.size() == 1 && countryLanguages.contains(language)) {
			
			countryRepository.delete(country);
			
			return;
		}
		
		countryLanguages.removeIf(l -> l.getName().equals(languageName));
		
		countryRepository.save(country);
		
		List<City> cities = cityRepository.findAllByLanguageNameAndCountryName(languageName, countryName);
		
		cities.forEach(c -> {
			
			List<Language> cityLanguages = c.getLanguages();
			
			if(cityLanguages.size() == 1 && cityLanguages.contains(language)) {
				cityRepository.delete(c);
			} else {
				cityLanguages.removeIf(l -> l.getName().equals(languageName));
			}	
		});
		
		cities.removeIf(c -> {
			
			List<Language> cityLanguages = c.getLanguages();
			
			return cityLanguages.size() == 1 && cityLanguages.contains(language);
		});
		
		cityRepository.saveAll(cities);
		
		List<String> citiesNames = new ArrayList<>();
		
		cities.forEach(c -> {
			citiesNames.add(c.getName());
		});
		
		List<EducationCenter> centers = educationCenterRepository.findAllByLanguageNameAndCitiesNames(languageName, citiesNames);
		
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
