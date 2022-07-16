package ru.blinov.language.spotter.course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.util.StringFormatter;

@Service
public class CourseService {
	
	private CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCoursesByCountryAndCityAndCenter(String countryName, String cityName, String centerName) {
		return courseRepository.findAll().stream()
				.filter(course -> course.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.filter(course -> course.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(course -> course.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCoursesByLanguageAndCountryAndCityAndCenter(String languageName, String countryName, String cityName, String centerName) {	
		return courseRepository.findAll().stream()
				.filter(course -> course.getLanguage().getName().equals(StringFormatter.formatPathVariable(languageName)))
				.filter(course -> course.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.filter(course -> course.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(course -> course.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void saveCourseOfCenterOfCityOfCountryOfLanguageToLearn(String languageName, String countryName, String cityName, String centerName, Course course) {
		
		Optional<Course> filteredCourse = courseRepository.findAll().stream()
				.filter(c -> c.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.filter(c -> c.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(c -> c.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.findAny();
		
		if(filteredCourse.isEmpty()) {
			throw new RuntimeException("There are no such country/city/center");
		}
		
		course.setEducationCenter(filteredCourse.get().getEducationCenter());
		
		Optional<Language> filteredLanguage = filteredCourse.get().getEducationCenter().getLanguages().stream()
				.filter(language -> language.getName().equals(StringFormatter.formatPathVariable(languageName)))
				.findAny();
		
		if(filteredLanguage.isEmpty()) {
			throw new RuntimeException("Language with name '" + StringFormatter.formatPathVariable(languageName) 
									   + "' is not present in education center: " + StringFormatter.formatPathVariable(centerName) 
									   + ", city: " + StringFormatter.formatPathVariable(cityName) 
									   + ", country: " + StringFormatter.formatPathVariable(countryName));
		}

		course.setLanguage(filteredLanguage.get());
		
		courseRepository.save(course);
	}
	
	@Transactional
	public void deleteCourseOfCenterOfCityOfCountryOfLanguageToLearn(String languageName, String countryName, String cityName,
																	 String centerName, int courseId) {
		
		List<Course> filteredCourses = courseRepository.findAll().stream()
				.filter(c -> c.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.filter(c -> c.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(c -> c.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.collect(Collectors.toList());
		
		if(filteredCourses.isEmpty()) {
			throw new RuntimeException("There are no such country/city/center");
		}
		
		if(filteredCourses.stream().filter(c -> c.getEducationCenter().hasLanguage(languageName)).findAny().isEmpty()) {
			throw new RuntimeException("There are no language with name '" + StringFormatter.formatPathVariable(languageName) 
									   + "' in education center: " + StringFormatter.formatPathVariable(centerName) 
									   + ", city: " + StringFormatter.formatPathVariable(cityName)
									   + ", country: " + StringFormatter.formatPathVariable(countryName));
		}
		
		Optional<Course> course = filteredCourses.stream()
				.filter(c -> c.getId() == courseId)
				.findAny();
		
		if(course.isEmpty()) {
			throw new RuntimeException("Course with id " + courseId 
									   + " in education center: " + StringFormatter.formatPathVariable(centerName) 
									   + ", city: " + StringFormatter.formatPathVariable(cityName) 
									   + ", country: " + StringFormatter.formatPathVariable(countryName) 
									   + " does not exist");
		}
		
		courseRepository.deleteById(courseId);
	}	
}
