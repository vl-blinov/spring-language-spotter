package ru.blinov.language.spotter.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.country.CountryRepository;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseRepository;
import ru.blinov.language.spotter.education.EducationCenter;
import ru.blinov.language.spotter.education.EducationCenterRepository;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;

@Service
public class CityService {
	
	private LanguageRepository languageRepository;
	
	private CountryRepository countryRepository;
	
	private CityRepository cityRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	private CourseRepository courseRepository;
	
	@Autowired
	public CityService(LanguageRepository languageRepository, CountryRepository countryRepository,
					   CityRepository cityRepository, EducationCenterRepository educationCenterRepository,
					   CourseRepository courseRepository) {
		
		this.languageRepository = languageRepository;
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
		this.educationCenterRepository = educationCenterRepository;
		this.courseRepository = courseRepository;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCities(String countryName, String languageName) {
		return cityRepository.findAllByLanguageNameAndCountryName(languageName, countryName);
	}

	@Transactional
	public void saveCity(City city) {
		cityRepository.save(city);
	}
	
	@Transactional
	public void deleteCity(String languageName, String countryName, String cityName) {
		
		//>>>1
		
		Optional<Language> languageOptional = languageRepository.findByName(languageName);
		
		if(languageOptional.isEmpty()) {
			throw new RuntimeException("Language with name '" + languageName + "' is not found");
		}
		
		Language language = languageOptional.get();
		
		Optional<Country> countryOptional = countryRepository.findByName(countryName);
		
		if(countryOptional.isEmpty()) {
			throw new RuntimeException("Country with name '" + countryName + "' is not found");
		}
		
		Country country = countryOptional.get();
		
		Optional<City> cityOptional = cityRepository.findByName(cityName);
		
		if(cityOptional.isEmpty()) {
			throw new RuntimeException("City with name '" + cityName + "' is not found");
		}
		
		City city = cityOptional.get();
		
		//>>>2
		
		List<Country> countries = language.getCountries();
		
		if(!countries.contains(country)) {
			throw new RuntimeException("Country with name '" + countryName + "' for language with name '" + languageName + "' is not found");
		}
		
		List<City> countryCities = country.getCities();
		
		if(!countryCities.contains(city)) {
			throw new RuntimeException("City with name '" + cityName + "' in country with name '" + countryName + "' is not found");
		}
		
		List<City> languageCities = language.getCities();
		
		if(!languageCities.contains(city)) {
			throw new RuntimeException("City with name '" + cityName + "' for language with name '" + languageName + "' is not found");
		}
		
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
