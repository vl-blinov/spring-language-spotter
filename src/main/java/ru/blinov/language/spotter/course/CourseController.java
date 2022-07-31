package ru.blinov.language.spotter.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public List<Course> findAllCourses(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
			   						   @PathVariable String centerName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		return courseService.findAllCourses(languageName, countryName, cityName, centerName);
	}
	
	@PostMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public Course addCourse(@RequestBody Course course) {
		
		courseService.saveCourse(course);
		
		return course;
	}
	
	@PutMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public Course updateCourse(@RequestBody Course course) {
		
		courseService.saveCourse(course);
		
		return course;
	}
	
	@DeleteMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses/{courseId}")
	public String deleteCourse(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
							   @PathVariable String centerName, @PathVariable int courseId, HttpServletRequest request) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);

		courseService.deleteCourse(languageName, countryName, cityName, centerName, courseId, request);
		
		return "Course with id " + courseId + " was deleted";
	}
}
