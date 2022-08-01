package ru.blinov.language.spotter.center;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseRepository;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;
import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class EducationCenterService {
	
	private LanguageRepository languageRepository;
	
	private EducationCenterRepository educationCenterRepository;
	
	private CourseRepository courseRepository;
	
	private RequestValidator requestValidator;

	@Autowired
	public EducationCenterService(LanguageRepository languageRepository, EducationCenterRepository educationCenterRepository,
								  CourseRepository courseRepository, RequestValidator requestValidator) {
		
		this.languageRepository = languageRepository;
		this.educationCenterRepository = educationCenterRepository;
		this.courseRepository = courseRepository;
		this.requestValidator = requestValidator;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllEducationCenters(String languageName, String countryName, String cityName) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName);
		
		return educationCenterRepository.findAllByLanguageNameAndCityName(languageName, cityName);
	}

	@Transactional(readOnly = true)
	public EducationCenter findEducationCenter(String languageName, String countryName, String cityName, String centerName) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName, centerName);
		
		return educationCenterRepository.findByName(centerName).get();
	}
	
	@Transactional
	public void saveEducationCenter(String languageName, String countryName, String cityName, EducationCenter center) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName);
		
		educationCenterRepository.save(center);
	}
	
	@Transactional
	public void deleteEducationCenter(String languageName, String countryName, String cityName, String centerName) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName, centerName);
		
		Language language = languageRepository.findByName(languageName).get();
		
		EducationCenter center = educationCenterRepository.findByName(centerName).get();
		
		List<Language> centerLanguages = center.getLanguages();
		
		if(centerLanguages.size() == 1 && centerLanguages.contains(language)) {
			
			educationCenterRepository.delete(center);
			
			return;
		}
		
		centerLanguages.removeIf(l -> l.getName().equals(languageName));
		
		educationCenterRepository.save(center);
		
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
