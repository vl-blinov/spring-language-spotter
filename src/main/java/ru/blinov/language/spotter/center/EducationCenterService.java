package ru.blinov.language.spotter.center;

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
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;

@Service
public class EducationCenterService {
	
	private LanguageRepository languageRepository;
	
	private CountryRepository countryRepository;
	
	private CityRepository cityRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	private CourseRepository courseRepository;

	@Autowired
	public EducationCenterService(LanguageRepository languageRepository, CountryRepository countryRepository,
								  CityRepository cityRepository, EducationCenterRepository educationCenterRepository,
								  CourseRepository courseRepository) {
		
		this.languageRepository = languageRepository;
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
		this.educationCenterRepository = educationCenterRepository;
		this.courseRepository = courseRepository;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllEducationCenters(String cityName, String languageName) {
		return educationCenterRepository.findAllByCityNameAndLanguageName(cityName, languageName);
	}

	@Transactional(readOnly = true)
	public EducationCenter findEducationCenter(String languageName, String centerName) {
		
		Optional<EducationCenter> center = educationCenterRepository.findByLanguageNameAndCenterName(languageName, centerName);
		
		if(center.isEmpty()) {
			throw new RuntimeException("Education center with name '" + centerName + "' with language name '" + languageName + "' is not found");
		}
		
		return center.get();
	}
	
	@Transactional
	public void saveEducationCenter(EducationCenter center) {
		educationCenterRepository.save(center);
	}
	
	@Transactional
	public void deleteEducationCenter(String languageName, String countryName, String cityName, String centerName) {
		
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
		
		Optional<EducationCenter> centerOptional = educationCenterRepository.findByName(centerName);
		
		if(centerOptional.isEmpty()) {
			throw new RuntimeException("Education center with name '" + cityName + "' is not found");
		}
		
		EducationCenter center = centerOptional.get();
		
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
		
		List<EducationCenter> cityCenters = city.getEducationCenters();
		
		if(!cityCenters.contains(center)) {
			throw new RuntimeException("Education center with name '" + centerName + "' in city with name '" + cityName + "' is not found");
		}
		
		List<EducationCenter> languageCenters = language.getEducationCenters();
		
		if(!languageCenters.contains(center)) {
			throw new RuntimeException("Education center with name '" + centerName + "' for language with name '" + languageName + "' is not found");
		}
		
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
