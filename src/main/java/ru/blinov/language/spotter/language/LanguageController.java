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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ru.blinov.language.spotter.util.StringFormatter;

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
	
	@PostMapping("/languages")
	public ResponseEntity<Object> addLanguage(@Valid @RequestBody Language language) {
		
		languageService.saveLanguage(language);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(language.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/languages")
	public void updateLanguage(@Valid @RequestBody Language language) {
		languageService.saveLanguage(language);
	}
	
	@DeleteMapping("/{languageName}")
	public ResponseEntity<Object> deleteLanguage(@PathVariable String languageName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		languageService.deleteLanguage(languageName);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
}
