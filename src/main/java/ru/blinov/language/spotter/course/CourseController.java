package ru.blinov.language.spotter.course;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api")
public class CourseController {
	
	private CourseService courseService;
	
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@GetMapping("/courses")
	@Operation(
			summary = "Fetches a list of courses.",
			description = "The method fetches a list of all courses available.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = Course.class))
							)
					)
			}
	)
	public List<Course> findAllCourses() {	
		return courseService.findAllCourses();
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/courses")
	@Operation(
			summary = "Fetches a list of courses filtered by a language, a country, a city and an education center.",
			description = "The method fetches a list of all courses filtered by a language, a country, a city and an education center.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = Course.class))
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Language is not found.\n\n"
									+ "Bad Request: Country is not found.\n\n"
									+ "Bad Request: City is not found.\n\n"
									+ "Bad Request: Education center is not found.\n\n"
									+ "Bad Request: Discrepancy between country and language. Country is not found for language.\n\n"
									+ "Bad Request: Discrepancy between city and country. City is not found for country.\n\n"
									+ "Bad Request: Discrepancy between city and language. City is not found for language.\n\n"
									+ "Bad Request: Discrepancy between education center and city. Education center is not found for city.\n\n"
									+ "Bad Request: Discrepancy between education center and language. Education center is not found for language.",
							content = @Content
					)
			}
	)
	public List<Course> findAllCourses(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
			@PathVariable String centerName) {	
		return courseService.findAllCourses(languageName, countryName, cityName, centerName);
	}
	
	@GetMapping("/courses/{courseId}")
	@Operation(
			summary = "Fetches a single course by ID.",
			description = "The method fetches a single course by ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = Course.class)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Course is not found.",
							content = @Content
					)
			}
	)
	public Course findCourse(@PathVariable Integer courseId) {
		return courseService.findCourse(courseId);
	}
	
	@PostMapping("/courses")
	@Operation(
			summary = "Adds a new course.",
			description = "The method adds a new course.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Add a new course",
											value = "{\"courseType\": \"string\","
													+ "\"studentsPerClass\": 0,"
													+ "\"courseDuration\": \"string\","
													+ "\"lessonsPerWeek\": 0,"
													+ "\"classTime\": \"string\","
													+ "\"lessonDuration\": \"string\","
													+ "\"ageRestriction\": \"string\","
													+ "\"entryLevel\": \"string\","
													+ "\"pricePerWeekAmount\": 0,"
													+ "\"pricePerWeekCurrency\": \"string\"}",
											description = "A JSON object of a new course."
														+ " The ID property is not provided."
														+ " A value for the ID will be assigned by the persistence provider when the new course is added."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created: the request has succeeded and has led to the creation of a new course.",
							content = @Content,
							headers = @Header(
									name = "Location",
									description = "A URL to a newly created course: http://localhost:8080/api/courses/{courseId}."
							)
					)
			}
	)
	public ResponseEntity<Object> addCourse(@Valid @RequestBody Course course) {
		
		courseService.saveCourse(course);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{courseId}")
                .buildAndExpand(course.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/courses")
	@Operation(
			summary = "Edits a course.",
			description = "The method edits a course.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Edit age restriction from 16+ y/o to 18+ y/o",
													value = "{\"id\": 1,"
															+ "\"courseType\": \"General English (Morning classes)\","
															+ "\"studentsPerClass\": 15,"
															+ "\"courseDuration\": \"1 - 12 weeks\","
															+ "\"lessonsPerWeek\": 15,"
															+ "\"classTime\": \"Monday - Friday 09:00 - 12:15\","
															+ "\"lessonDuration\": \"60 minutes\","
															+ "\"ageRestriction\": \"18+ years old\","
															+ "\"entryLevel\": \"Elementary (A1)\","
															+ "\"pricePerWeekAmount\": 200,"
															+ "\"pricePerWeekCurrency\": \"€\"}",
											description = "A JSON object of a course."
														+ " The \"ageRestriction\" property has an updated value \"18+ years old\"."
														+ " Other properties remain the same."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to the update of the course.",
							content = @Content
					)
			}
	)
	public void updateCourse(@Valid @RequestBody Course course) {
		courseService.saveCourse(course);
	}
	
	@DeleteMapping("/courses/{courseId}")
	@Operation(
			summary = "Deletes a course by ID.",
			description = "The method deletes a course by ID.",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "No Content: the request has succeeded and has led to the deletion of a course.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Course is not found.",
							content = @Content
					)
			}
	)
	public ResponseEntity<Object> deleteCourse(@PathVariable Integer courseId) {

		courseService.deleteCourse(courseId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
