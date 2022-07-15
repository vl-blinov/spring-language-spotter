package ru.blinov.language.spotter.language;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LanguageController {
	
	private LanguageService languageService;

	@Autowired
	public LanguageController(LanguageService languageService) {
		this.languageService = languageService;
	}
	
	@GetMapping("/languages")
	public List<Language> getAllLanguagesToLearn() {
		return languageService.findAllLanguages();
	}
	
	@PostMapping("/languages")
	public Language addLanguageToLearn(@RequestBody Language language) {
		
		languageService.addLanguage(language);
		
		return language;
	}
	
	@DeleteMapping("/languages/{languageName}")
	public String deleteLanguageToLearn(@PathVariable String languageName) {
		
		languageService.deleteLanguageByName(languageName);
		
		return "Language with name '" + languageName + "' was deleted";
	}	
}
