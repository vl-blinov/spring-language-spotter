package ru.blinov.language.spotter.city;

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
public class CityController {
	
	private CityService cityService;
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/cities")
	@Operation(
			summary = "Fetches a list of cities.",
			description = "The method fetches a list of all cities available to study in.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = City.class))
							)
					)
			}
	)
	public List<City> findAllCities() {
		return cityService.findAllCities();
	}
	
	@GetMapping("/{languageName}/{countryName}/cities")
	@Operation(
			summary = "Fetches a list of cities filtered by a language and a country.",
			description = "The method fetches a list of all cities filtered by a language and a country.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = City.class))
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Language is not found.\n\n"
										+ "Bad Request: Country is not found.\n\n"
										+ "Bad Request: Discrepancy between country and language. Country is not found for language.",
							content = @Content
					)
			}
	)
	public List<City> findAllCities(@PathVariable String languageName, @PathVariable String countryName) {
		return cityService.findAllCities(languageName, countryName);
	}
	
	@GetMapping("/cities/{cityId}")
	@Operation(
			summary = "Fetches a single city by ID.",
			description = "The method fetches a single city by ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = City.class)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: City is not found.",
							content = @Content
					)
			}
	)
	public City findCity(@PathVariable Integer cityId) {
		return cityService.findCity(cityId);
	}
	
	@PostMapping("/cities")
	@Operation(
			summary = "Adds a new city.",
			description = "The method adds a new city.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Add a new city",
											value = "{\"name\": \"string\"}",
											description = "A JSON object of a new city."
														+ " The ID property is not provided."
														+ " A value for the ID will be assigned by the persistence provider when the new city is added."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created: the request has succeeded and has led to the creation of a new city.",
							content = @Content,
							headers = @Header(
									name = "Location",
									description = "A URL to a newly created city: http://localhost:8080/api/cities/{cityId}."
							)
					)
			}
	)
	public ResponseEntity<Object> addCity(@Valid @RequestBody City city) {
		
		cityService.addCity(city);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{cityId}")
                .buildAndExpand(city.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PostMapping("/cities/{cityId}/languages/{languageId}")
	@Operation(
			summary = "Adds a language to a city by IDs.",
			description = "The method adds a language to a city by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to addition of the language to the city.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: City is not found.\n\n"
										+ "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public void addLanguageToCity(@PathVariable Integer cityId, @PathVariable Integer languageId) {
		cityService.addLanguageToCity(cityId, languageId);
	}
	
	@PostMapping("/cities/{cityId}/centers/{centerId}")
	@Operation(
			summary = "Adds an education center to a city by IDs.",
			description = "The method adds an education center to a city by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to addition of the education center to the city.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: City is not found.\n\n"
										+ "Bad Request: Education center is not found.",
							content = @Content
					)
			}
	)
	public void addEducationCenterToCity(@PathVariable Integer cityId, @PathVariable Integer centerId) {
		cityService.addEducationCenterToCity(cityId, centerId);
	}
	
	@DeleteMapping("/cities/{cityId}")
	@Operation(
			summary = "Deletes a city by ID.",
			description = "The method deletes a city by ID.",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "No Content: the request has succeeded and has led to the deletion of a city.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: City is not found.",
							content = @Content
					)
			}
	)
	public ResponseEntity<Object> deleteCity(@PathVariable Integer cityId) {
		
		cityService.deleteCity(cityId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/cities/{cityId}/languages/{languageId}")
	@Operation(
			summary = "Removes a language from a city by IDs.",
			description = "The method removes a language from a city by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to removal of the language from the city.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: City is not found.\n\n"
										+ "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public void removeLanguageFromCity(@PathVariable Integer cityId, @PathVariable Integer languageId) {
		cityService.removeLanguageFromCity(cityId, languageId);
	}
	
	@DeleteMapping("/cities/{cityId}/centers/{centerId}")
	@Operation(
			summary = "Removes an education center from a city by IDs.",
			description = "The method removes an education center from a city by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to removal of the education center from the city.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: City is not found.\n\n"
										+ "Bad Request: Education center is not found.",
							content = @Content
					)
			}
	)
	public void removeEducationCenterFromCity(@PathVariable Integer cityId, @PathVariable Integer centerId) {
		cityService.removeEducationCenterFromCity(cityId, centerId);
	}
}
