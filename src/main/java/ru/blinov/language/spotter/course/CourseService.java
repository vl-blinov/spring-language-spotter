package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CourseService {
	
	private CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCoursesByCenterAndCityAndCountryAndLanguageName(String languageName,
																			   String countryName,
																			   String cityName,
																			   String centerName) {
		return courseRepository.findAllByCenterAndCityAndCountryAndLanguageName(StringUtils.capitalize(languageName),
																				StringUtils.capitalize(countryName),
																				StringUtils.capitalize(cityName),
																				centerName);
	}
}
