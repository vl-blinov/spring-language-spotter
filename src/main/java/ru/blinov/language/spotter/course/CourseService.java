package ru.blinov.language.spotter.course;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.util.StringFormatter;

@Service
public class CourseService {
	
	private CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCoursesByLanguageAndCountryAndCityAndCenter(String languageName, String countryName, String cityName, String centerName) {	
		return courseRepository.findAll().stream()
				.filter(course -> course.hasLanguage(languageName))
				.filter(course -> course.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.filter(course -> course.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(course -> course.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.collect(Collectors.toList());
	}
	
}
