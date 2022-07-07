package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;

@Service
public class CourseService {
	
	private LanguageRepository languageRepository;

	@Autowired
	public CourseService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCoursesByCenterByCityByCountryByLanguageName(String languageName, String countryName, String cityName, String centerName) {	
		return getLanguage(languageName).getCountry(countryName).getCity(cityName).getEducationCenter(centerName).getCourses();
	}
	
	private Language getLanguage(String languageName) {
		return languageRepository.findByName(StringUtils.capitalize(languageName)).get();
	}
}
