package ru.blinov.language.spotter.center;

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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name ="4. Education center controller")
public class EducationCenterController {
	
	private EducationCenterService educationCenterService;
	
	@Autowired
	public EducationCenterController(EducationCenterService educationCenterService) {
		this.educationCenterService = educationCenterService;
	}
	
	@GetMapping("/centers")
	@Operation(
			summary = "Fetches a list of education centers.",
			description = "The method fetches a list of all education centers available to study in.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = EducationCenter.class))
							)
					)
			}
	)
	public List<EducationCenter> findAllEducationCenters() {
		return educationCenterService.findAllEducationCenters();
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/centers")
	@Operation(
			summary = "Fetches a list of education centers filtered by a language, a country and a city.",
			description = "The method fetches a list of all education centers filtered by a language, a country and a city.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = EducationCenter.class))
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Language is not found.\n\n"
										+ "Bad Request: Country is not found.\n\n"
										+ "Bad Request: City is not found.\n\n"
										+ "Bad Request: Discrepancy between country and language. Country is not found for language.\n\n"
										+ "Bad Request: Discrepancy between city and country. City is not found for country.\n\n"
										+ "Bad Request: Discrepancy between city and language. City is not found for language.",
							content = @Content
					)
			}
	)
	public List<EducationCenter> findAllEducationCenters(@PathVariable String languageName, @PathVariable String countryName,
			@PathVariable String cityName) {
		return educationCenterService.findAllEducationCenters(languageName, countryName, cityName);
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}")
	@Operation(
			summary = "Fetches a single education center filtered by a language, a country, a city and its name.",
			description = "The method fetches a single education center filtered by a language, a country, a city and its name.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = EducationCenter.class)
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
	public EducationCenter findEducationCenter(@PathVariable String languageName, @PathVariable String countryName,
			@PathVariable String cityName, @PathVariable String centerName) {
		return educationCenterService.findEducationCenter(languageName, countryName, cityName, centerName);
	}
	
	@GetMapping("/centers/{centerId}")
	@Operation(
			summary = "Fetches a single education center by ID.",
			description = "The method fetches a single education center by ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = EducationCenter.class)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.",
							content = @Content
					)
			}
	)
	public EducationCenter findEducationCenter(@PathVariable Integer centerId) {
		return educationCenterService.findEducationCenter(centerId);
	}
	
	@PostMapping("/centers")
	@Operation(
			summary = "Adds a new education center.",
			description = "The method adds a new education center.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Add a new education center",
											value = "{\"name\": \"string\","
													+ "\"address\": \"string\","
													+ "\"registrationFeeAmount\": 0,"
													+ "\"registrationFeeCurrency\": \"string\","
													+ "\"rating\": 0}",
											description = "A JSON object of a new education center."
														+ " The ID property is not provided."
														+ " A value for the ID will be assigned by the persistence provider when the new education center is added."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created: the request has succeeded and has led to the creation of a new education center.",
							content = @Content,
							headers = @Header(
									name = "Location",
									description = "A URL to a newly created education center: http://localhost:8080/api/centers/{centerId}."
							)
					)
			}
	)
	public ResponseEntity<Object> addEducationCenter(@Valid @RequestBody EducationCenter center) {
		
		educationCenterService.saveEducationCenter(center);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{centerId}")
                .buildAndExpand(center.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/centers")
	@Operation(
			summary = "Edits an education center.",
			description = "The method edits an education center.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Edit rating from 4.9 to 4.7",
											value = "{\"id\": 1,"
													+ "\"name\": \"Erin School Of English\","
													+ "\"address\": \"43 N Great George's St, Rotunda, Dublin, D01 N6P2, Ireland\","
													+ "\"registrationFeeAmount\": 85,"
													+ "\"registrationFeeCurrency\": \"€\","
													+ "\"rating\": 4.7}",
											description = "A JSON object of an education center."
														+ " The \"rating\" property has an updated value 4.7."
														+ " Other properties remain the same."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to the update of the education center",
							content = @Content
					)
			}
	)
	public void updateEducationCenter(@Valid @RequestBody EducationCenter center) {
		educationCenterService.saveEducationCenter(center);
	}
	
	@PostMapping("/centers/{centerId}/languages/{languageId}")
	@Operation(
			summary = "Adds a language to an education center by IDs.",
			description = "The method adds a language to an education center by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to addition of the language to the education center.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.\n\n"
										+ "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public void addLanguageToEducationCenter(@PathVariable Integer centerId, @PathVariable Integer languageId) {
		educationCenterService.addLanguageToEducationCenter(centerId, languageId);
	}
	
	@PostMapping("/centers/{centerId}/courses/{courseId}")
	@Operation(
			summary = "Adds a course to an education center by IDs.",
			description = "The method adds a course to an education center by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to addition of the course to the education center.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.\n\n"
										+ "Bad Request: Course is not found.",
							content = @Content
					)
			}
	)
	public void addCourseToEducationCenter(@PathVariable Integer centerId, @PathVariable Integer courseId) {
		educationCenterService.addCourseToEducationCenter(centerId, courseId);
	}
	
	@PostMapping("/centers/{centerId}/accommodations/{accommodationId}")
	@Operation(
			summary = "Adds an accommodation to an education center by IDs.",
			description = "The method adds an accommodation to an education center by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to addition of the accommodation to the education center.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.\n\n"
										+ "Bad Request: Accommodation is not found.",
							content = @Content
					)
			}
	)
	public void addAccommodationToEducationCenter(@PathVariable Integer centerId, @PathVariable Integer accommodationId) {
		educationCenterService.addAccommodationToEducationCenter(centerId, accommodationId);
	}
	
	@DeleteMapping("/centers/{centerId}")
	@Operation(
			summary = "Deletes an education center by ID.",
			description = "The method deletes an education center by ID.",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "No Content: the request has succeeded and has led to the deletion of an education center.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.",
							content = @Content
					)
			}
	)
	public ResponseEntity<Object> deleteEducationCenter(@PathVariable Integer centerId) {
		
		educationCenterService.deleteEducationCenter(centerId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/centers/{centerId}/languages/{languageId}")
	@Operation(
			summary = "Removes a language from an education center by IDs.",
			description = "The method removes a language from an education center by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to removal of the language from the education center.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.\n\n"
										+ "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public void removeLanguageFromEducationCenter(@PathVariable Integer centerId, @PathVariable Integer languageId) {
		educationCenterService.removeLanguageFromEducationCenter(centerId, languageId);
	}
	
	@DeleteMapping("/centers/{centerId}/courses/{courseId}")
	@Operation(
			summary = "Removes a course from an education center by IDs.",
			description = "The method removes a course from an education center by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to removal of the course from the education center.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.\n\n"
										+ "Bad Request: Course is not found.",
							content = @Content
					)
			}
	)
	public void removeCourseFromEducationCenter(@PathVariable Integer centerId, @PathVariable Integer courseId) {
		educationCenterService.removeCourseFromEducationCenter(centerId, courseId);
	}
	
	@DeleteMapping("/centers/{centerId}/accommodations/{accommodationId}")
	@Operation(
			summary = "Removes an accommodation from an education center by IDs.",
			description = "The method removes an accommodation from an education center by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to removal of the accommodation from the education center.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Education center is not found.\n\n"
										+ "Bad Request: Accommodation is not found.",
							content = @Content
					)
			}
	)
	public void removeAccommodationFromEducationCenter(@PathVariable Integer centerId, @PathVariable Integer accommodationId) {
		educationCenterService.removeAccommodationFromEducationCenter(centerId, accommodationId);
	}
}
