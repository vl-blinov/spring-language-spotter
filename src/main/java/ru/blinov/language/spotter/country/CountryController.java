package ru.blinov.language.spotter.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CountryController {
	
	private CountryService countryService;
	
	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}
	
	@GetMapping("/{languageName}/countries")
	public List<Country> getAllCountriesForLanguageToLearn(@PathVariable String languageName) {	
		return countryService.findAllCountriesByLanguageName(languageName);
	}
}
