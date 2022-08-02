package ru.blinov.language.spotter.language;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class LanguageController {
	
	private LanguageService languageService;

	@Autowired
	public LanguageController(LanguageService languageService) {
		this.languageService = languageService;
	}
	
	@GetMapping("/languages")
	public List<Language> findAllLanguages() {
		return languageService.findAllLanguages();
	}
	
	@GetMapping("/languages/{languageId}")
	public Language findLanguage(@PathVariable Integer languageId) {
		return languageService.findLanguage(languageId);
	}
	
	@PostMapping("/languages")
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
	public ResponseEntity<Object> deleteLanguage(@PathVariable Integer languageId) {
		
		languageService.deleteLanguage(languageId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
}
