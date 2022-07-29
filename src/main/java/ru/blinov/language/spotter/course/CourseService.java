package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.validator.UrlValidator;

@Service
public class CourseService {
	
	private CourseRepository courseRepository;
	
	private UrlValidator urlValidator;

	@Autowired
	public CourseService(CourseRepository courseRepository, UrlValidator urlValidator) {
		
		this.courseRepository = courseRepository;
		this.urlValidator = urlValidator;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCourses(String languageName, String countryName, String cityName, String centerName) {
		
		urlValidator.checkLanguageAndCountryAndCityAndCenter(languageName, countryName, cityName, centerName);
		
		return courseRepository.findAllByLanguageNameAndCenterName(languageName, centerName);
	}
	
	@Transactional
	public void saveCourse(Course course) {
		courseRepository.save(course);	
	}
	
	@Transactional
	public void deleteCourse(String languageName, String countryName, String cityName, String centerName, int courseId) {
		
		urlValidator.checkLanguageAndCountryAndCityAndCenterAndCourse(languageName, countryName, cityName, centerName, courseId);
		
		courseRepository.deleteById(courseId);
	}	
}
