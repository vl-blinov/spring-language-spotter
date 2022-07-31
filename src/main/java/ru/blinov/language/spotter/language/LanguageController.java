package ru.blinov.language.spotter.language;

import java.util.List;

import javax.validation.Valid;

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
	public Language addLanguage(@Valid @RequestBody Language language) {
		
		languageService.saveLanguage(language);
		
		return language;
	}
	
	@PutMapping("/languages")
	public Language updateLanguage(@Valid @RequestBody Language language) {
		
		languageService.saveLanguage(language);
		
		return language;
	}
	
	@DeleteMapping("/{languageName}")
	public String deleteLanguage(@PathVariable String languageName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		languageService.deleteLanguage(languageName);
		
		return "Language with name '" + languageName + "' was deleted";
	}	
}
