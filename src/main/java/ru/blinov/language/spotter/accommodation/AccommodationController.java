package ru.blinov.language.spotter.accommodation;

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
public class AccommodationController {
	
	private AccommodationService accommodationService;
	
	@Autowired
	public AccommodationController(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}
	
	@GetMapping("/accommodations")
	@Operation(
			summary = "Fetches a list of accommodations.",
			description = "The method fetches a list of all accommodations available.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = Accommodation.class))
							)
					)
			}
	)
	public List<Accommodation> findAllAccommodations() {
		return accommodationService.findAllAccommodations();
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	@Operation(
			summary = "Fetches a list of accommodations filtered by a language, a country, a city and an education center.",
			description = "The method fetches a list of all accommodations filtered by a language, a country, a city and an education center.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = Accommodation.class))
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
	public List<Accommodation> findAllAccommodations(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
			@PathVariable String centerName) {
		return accommodationService.findAllAccommodations(languageName, countryName, cityName, centerName);
	}
	
	@GetMapping("/accommodations/{accommodationId}")
	@Operation(
			summary = "Fetches a single accommodation by ID.",
			description = "The method fetches a single accommodation by ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = Accommodation.class)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Accommodation is not found.",
							content = @Content
					)
			}
	)
	public Accommodation findAccommodation(@PathVariable Integer accommodationId) {
		return accommodationService.findAccommodation(accommodationId);
	}
	
	@PostMapping("/accommodations")
	@Operation(
			summary = "Adds a new accommodation.",
			description = "The method adds a new accommodation.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Add a new accommodation",
											value = "{\"accommodationType\": \"string\","
													+ "\"roomType\": \"string\","
													+ "\"mealIncludedType\": \"string\","
													+ "\"ageRestriction\": \"string\","
													+ "\"pricePerWeekAmount\": 0,"
													+ "\"pricePerWeekCurrency\": \"string\"}",
											description = "A JSON object of a new accommodation."
														+ " The ID property is not provided."
														+ " A value for the ID will be assigned by the persistence provider when the new accommodation is added."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created: the request has succeeded and has led to the creation of a new accommodation.",
							content = @Content,
							headers = @Header(
									name = "Location",
									description = "A URL to a newly created accommodation: http://localhost:8080/api/accommodations/{accommodationId}."
							)
					)
			}
	)
	public ResponseEntity<Object> addAccommodation(@Valid @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodation(accommodation);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{accommodationId}")
                .buildAndExpand(accommodation.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();	
	}
	
	@PutMapping("/accommodations")
	@Operation(
			summary = "Edits an accommodation.",
			description = "The method edits an accommodation.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Edit price from €260 to €240",
													value = "{\"id\": 1,"
															+ "\"accommodationType\": \"Homestay\","
															+ "\"roomType\": \"Single room\","
															+ "\"mealIncludedType\": \"Half board\","
															+ "\"ageRestriction\": \"16+ years old\","
															+ "\"pricePerWeekAmount\": 240,"
															+ "\"pricePerWeekCurrency\": \"€\"}",
											description = "A JSON object of an accommodation."
														+ " The \"pricePerWeekAmount\" property has an updated value 240."
														+ " Other properties remain the same."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to the update of the accommodation.",
							content = @Content
					)
			}
	)
	public void updateAccommodation(@Valid @RequestBody Accommodation accommodation) {
		accommodationService.saveAccommodation(accommodation);
	}
	
	@DeleteMapping("/accommodations/{accommodationId}")
	@Operation(
			summary = "Deletes an accommodation by ID.",
			description = "The method deletes an accommodation by ID.",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "No Content: the request has succeeded and has led to the deletion of an accommodation.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Accommodation is not found.",
							content = @Content
					)
			}
	)
	public ResponseEntity<Object> deleteAccommodation(@PathVariable Integer accommodationId) {
		
		accommodationService.deleteAccommodation(accommodationId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
