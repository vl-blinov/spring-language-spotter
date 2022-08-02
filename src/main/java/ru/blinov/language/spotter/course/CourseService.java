package ru.blinov.language.spotter.course;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.util.StringFormatter;
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
	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCourses(String languageName, String countryName, String cityName, String centerName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName, centerName);
		
		return courseRepository.findAllByLanguageNameAndCenterName(languageName, centerName);
	}

	@Transactional
	public Course findCourse(Integer courseId) {
		
		Optional<Course> courseOptional = courseRepository.findById(courseId);
		
		if(courseOptional.isEmpty()) {
			throw new RequestUrlException("Course with id '" + courseId + "' is not found");
		}
		
		Course course = courseOptional.get();
		
		return course;
	}	
	
	@Transactional
	public void saveCourse(Course course) {
		courseRepository.save(course);	
	}
	
	@Transactional
	public void deleteCourse(Integer courseId) {
		
		Course course = findCourse(courseId);
		
		courseRepository.delete(course);
	}
}
