package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CourseController {
	
	private CourseService courseService;
	
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public List<Course> getAllCoursesForCenterAndCityAndCountryAndLanguageToLearn(@PathVariable String languageName,
																		 		  @PathVariable String countryName,
																		 		  @PathVariable String cityName,
																		 		  @PathVariable String centerName) {
		return courseService.findAllCoursesByCenterAndCityAndCountryAndLanguageName(languageName, countryName, cityName, centerName);
	}

}
