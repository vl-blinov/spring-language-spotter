package ru.blinov.language.spotter.language;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.city.CityRepository;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.country.CountryRepository;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseRepository;
import ru.blinov.language.spotter.education.EducationCenter;
import ru.blinov.language.spotter.education.EducationCenterRepository;

@Service
public class LanguageService {
	
	private LanguageRepository languageRepository;
	
	private CountryRepository countryRepository;
	
	private CityRepository cityRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	private CourseRepository courseRepository;

	@Autowired
	public LanguageService(LanguageRepository languageRepository, CountryRepository countryRepository,
						   CityRepository cityRepository, EducationCenterRepository educationCenterRepository,
						   CourseRepository courseRepository) {
		
		this.languageRepository = languageRepository;
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
		this.educationCenterRepository = educationCenterRepository;
		this.courseRepository = courseRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Language> findAllLanguages() {
		return languageRepository.findAll();
	}
	
	public void saveLanguage(Language language) {
		languageRepository.save(language);
	}
	
	@Transactional
	public void deleteLanguage(String languageName) {
		
		//>>>1
		
		Optional<Language> languageOptional = languageRepository.findByName(languageName);
		
		if(languageOptional.isEmpty()) {
			throw new RuntimeException("Language with name '" + languageName + "' is not found");
		}
		
		Language language = languageOptional.get();

		//>>>2
		
		List<Country> countries = language.getCountries();

		countries.forEach(c -> {
			
			List<Language> countryLanguages = c.getLanguages();
			
			if(countryLanguages.size() == 1 && countryLanguages.contains(language)) {
				countryRepository.delete(c);
			} else {
				countryLanguages.removeIf(l -> l.getName().equals(languageName));
			}
		});
		
		countries.removeIf(c -> {
			
			List<Language> countryLanguages = c.getLanguages();
			
			return countryLanguages.size() == 1 && countryLanguages.contains(language);
		});
		
		countryRepository.saveAll(countries);

		//>>>3
		
		List<City> cities = language.getCities();
		
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

		//>>>4
		
		List<EducationCenter> centers = language.getEducationCenters();
		
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
		
		List<Course> courses = language.getCourses();
		
		courses.forEach(c -> {
			if(c.getLanguage().getName().equals(languageName)) {
				courseRepository.delete(c);
			}
		});
		
		courses.removeIf(c -> c.getLanguage().getName().equals(languageName));
		
		courseRepository.saveAll(courses);
		
		//>>>6
		
		languageRepository.delete(language);
	}
}
