package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class CourseService {
	
	private CourseRepository courseRepository;
	
	private RequestValidator requestValidator;

	@Autowired
	public CourseService(CourseRepository courseRepository, RequestValidator requestValidator) {
		
		this.courseRepository = courseRepository;
		this.requestValidator = requestValidator;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCourses(String languageName, String countryName, String cityName, String centerName) {
		
		requestValidator.checkLanguageAndCountryAndCityAndCenter(languageName, countryName, cityName, centerName);
		
		return courseRepository.findAllByLanguageNameAndCenterName(languageName, centerName);
	}
	
	@Transactional
	public void saveCourse(Course course) {
		courseRepository.save(course);	
	}
	
	@Transactional
	public void deleteCourse(String languageName, String countryName, String cityName, String centerName, int courseId) {
		
		requestValidator.checkLanguageAndCountryAndCityAndCenterAndCourse(languageName, countryName, cityName, centerName, courseId);
		
		courseRepository.deleteById(courseId);
	}	
}
