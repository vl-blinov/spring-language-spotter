package ru.blinov.language.spotter.country;

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
public class CountryController {
	
	private CountryService countryService;
	
	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}
	
	@GetMapping("/countries")
	@Operation(
			summary = "Fetches a list of countries.",
			description = "The method fetches a list of all countries available to study in.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = Country.class))
							)
					)
			}	
	)
	public List<Country> findAllCountries() {	
		return countryService.findAllCountries();
	}
	
	@GetMapping("/{languageName}/countries")
	@Operation(
			summary = "Fetches a list of countries filtered by a language.",
			description = "The method fetches a list of all countries filtered by a language.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = Country.class))
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public List<Country> findAllCountries(@PathVariable String languageName) {	
		return countryService.findAllCountries(languageName);
	}
	
	@GetMapping("/countries/{countryId}")
	@Operation(
			summary = "Fetches a single country by ID.",
			description = "The method fetches a single country by ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = Country.class)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Country is not found.",
							content = @Content
					)
			}
	)
	public Country findCountry(@PathVariable Integer countryId) {
		return countryService.findCountry(countryId);
	}
	
	@PostMapping("/countries")
	@Operation(
			summary = "Adds a new country.",
			description = "The method adds a new country.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Add a new country",
											value = "{\"name\": \"string\"}",
											description = "A JSON object of a new country."
														+ " The ID property is not provided."
														+ " A value for the ID will be assigned by the persistence provider when the new country is added."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created: the request has succeeded and has led to the creation of a new country.",
							content = @Content,
							headers = @Header(
									name = "Location",
									description = "A URL to a newly created country: http://localhost:8080/api/countries/{countryId}."
							)
					)
			}
	)
	public ResponseEntity<Object> addCountry(@Valid @RequestBody Country country) {
		
		countryService.addCountry(country);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{countryId}")
                .buildAndExpand(country.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PostMapping("/countries/{countryId}/languages/{languageId}")
	@Operation(
			summary = "Adds a language to a country by IDs.",
			description = "The method adds a language to a country by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to addition of the language to the country.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Country is not found.\n\n"
										+ "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public void addLanguageToCountry(@PathVariable Integer countryId, @PathVariable Integer languageId) {
		countryService.addLanguageToCountry(countryId, languageId);
	}
	
	@PostMapping("/countries/{countryId}/cities/{cityId}")
	@Operation(
			summary = "Adds a city to a country by IDs.",
			description = "The method adds a city to a country by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to addition of the city to the country.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Country is not found.\n\n"
										+ "Bad Request: City is not found.",
							content = @Content
					)
			}
	)
	public void addCityToCountry(@PathVariable Integer countryId, @PathVariable Integer cityId) {
		countryService.addCityToCountry(countryId, cityId);
	}

	@DeleteMapping("/countries/{countryId}")
	@Operation(
			summary = "Deletes a country by ID.",
			description = "The method deletes a country by ID.",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "No Content: the request has succeeded and has led to the deletion of a country.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Country is not found.",
							content = @Content
					)
			}
	)
	public ResponseEntity<Object> deleteCountry(@PathVariable Integer countryId) {
		
		countryService.deleteCountry(countryId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/countries/{countryId}/languages/{languageId}")
	@Operation(
			summary = "Removes a language from a country by IDs.",
			description = "The method removes a language from a country by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to removal of the language from the country.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Country is not found.\n\n"
										+ "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public void removeLanguageFromCountry(@PathVariable Integer countryId, @PathVariable Integer languageId) {
		countryService.removeLanguageFromCountry(countryId, languageId);
	}
	
	@DeleteMapping("/countries/{countryId}/cities/{cityId}")
	@Operation(
			summary = "Removes a city from a country by IDs.",
			description = "The method removes a city from a country by IDs.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded and has led to removal of the city from the country.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Country is not found.\n\n"
										+ "Bad Request: City is not found.",
							content = @Content
					)
			}
	)
	public void removeCityFromCountry(@PathVariable Integer countryId, @PathVariable Integer cityId) {
		countryService.removeCityFromCountry(countryId, cityId);
	}
}
