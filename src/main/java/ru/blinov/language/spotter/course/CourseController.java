package ru.blinov.language.spotter.course;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class CourseController {
	
	private CourseService courseService;
	
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@GetMapping("/courses")
	public List<Course> findAllCourses() {	
		return courseService.findAllCourses();
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	public List<Course> findAllCourses(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
			@PathVariable String centerName) {	
		return courseService.findAllCourses(languageName, countryName, cityName, centerName);
	}
	
	@GetMapping("/courses/{courseId}")
	public Course findCourse(@PathVariable Integer courseId) {
		return courseService.findCourse(courseId);
	}
	
	@PostMapping("/courses")
	public ResponseEntity<Object> addCourse(@Valid @RequestBody Course course) {
		
		courseService.saveCourse(course);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/courses")
	public void updateCourse(@Valid @RequestBody Course course) {
		courseService.saveCourse(course);
	}
	
	@DeleteMapping("/courses/{courseId}")
	public ResponseEntity<Object> deleteCourse(@PathVariable Integer courseId) {

		courseService.deleteCourse(courseId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
