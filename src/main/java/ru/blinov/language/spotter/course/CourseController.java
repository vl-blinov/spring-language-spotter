package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.blinov.language.spotter.util.StringFormatter;

@RestController
@RequestMapping("/api")
public class CourseController {
	
	private CourseService courseService;
	
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@GetMapping("/{countryName}/{cityName}/{centerName}/courses")
	public List<Course> getAllCoursesOfCenterOfCityOfCountry(@PathVariable String countryName, @PathVariable String cityName,
															 @PathVariable String centerName) {
		return courseService.findAllCoursesByCountryAndCityAndCenter(countryName, cityName, centerName);
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public List<Course> getAllCoursesOfCenterOfCityOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName,
																			  @PathVariable String cityName, @PathVariable String centerName) {
		return courseService.findAllCoursesByLanguageAndCountryAndCityAndCenter(languageName, countryName, cityName, centerName);
	}
	
	@PostMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public Course addCourseToCenterOfCityOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName,
			  														@PathVariable String cityName, @PathVariable String centerName, @RequestBody Course course) {
		
		courseService.saveCourseOfCenterOfCityOfCountryOfLanguageToLearn(languageName, countryName, cityName, centerName, course);
		
		return course;
	}
	
	@PutMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public Course updateCourseOfCenterOfCityOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName,
			  														   @PathVariable String cityName, @PathVariable String centerName, @RequestBody Course course) {
		
		courseService.saveCourseOfCenterOfCityOfCountryOfLanguageToLearn(languageName, countryName, cityName, centerName, course);
		
		return course;
	}
	
	@DeleteMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses/{courseId}")
	public String deleteCourseOfCenterOfCityOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName,
			   														   @PathVariable String cityName, @PathVariable String centerName, 
			   														   @PathVariable int courseId) {
		
		courseService.deleteCourseOfCenterOfCityOfCountryOfLanguageToLearn(languageName, countryName, cityName, centerName, courseId);
		
		return "Course with id " + courseId 
			   + " in education center: " + StringFormatter.formatPathVariable(centerName) 
			   + ", city: " + StringFormatter.formatPathVariable(cityName) 
			   + ", country: " + StringFormatter.formatPathVariable(countryName) 
			   + " was deleted";
	}
}
