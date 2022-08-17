package ru.blinov.language.spotter.language;

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
public class LanguageController {
	
	private LanguageService languageService;

	@Autowired
	public LanguageController(LanguageService languageService) {
		this.languageService = languageService;
	}
	
	@GetMapping("/languages")
	@Operation(
			summary = "Fetches a list of languages.",
			description = "The method fetches a list of all languages available for learning.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = Language.class))
							)
					)
			}
	)
	public List<Language> findAllLanguages() {
		return languageService.findAllLanguages();
	}
	
	@GetMapping("/languages/{languageId}")
	@Operation(
			summary = "Fetches a single language by ID.",
			description = "The method fetches a single language by ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK: the request has succeeded.",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = Language.class)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public Language findLanguage(@PathVariable Integer languageId) {
		return languageService.findLanguage(languageId);
	}
	
	@PostMapping("/languages")
	@Operation(
			summary = "Adds a new language.",
			description = "The method adds a new language.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							examples = {
									@ExampleObject(
											name = "Add a new language",
											value = "{\"name\": \"string\"}",
											description = "A JSON object of a new language."
														+ " The ID property is not provided."
														+ " A value for the ID will be assigned by the persistence provider when the new language is added."
									)
							}
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Created: the request has succeeded and has led to the creation of a new language.",
							content = @Content,
							headers = @Header(
									name = "Location",
									description = "A URL to a newly created language: http://localhost:8080/api/languages/{languageId}."
							)
					)
			}
	)
	public ResponseEntity<Object> addLanguage(@Valid @RequestBody Language language) {
		
		languageService.addLanguage(language);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{languageId}")
                .buildAndExpand(language.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@DeleteMapping("/languages/{languageId}")
	@Operation(
			summary = "Deletes a language by ID.",
			description = "The method deletes a language by ID.",
			responses = {
					@ApiResponse(
							responseCode = "204",
							description = "No Content: the request has succeeded and has led to the deletion of a language.",
							content = @Content
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request: Language is not found.",
							content = @Content
					)
			}
	)
	public ResponseEntity<Object> deleteLanguage(@PathVariable Integer languageId) {
		
		languageService.deleteLanguage(languageId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
}
