package ru.blinov.language.spotter.country;

import java.util.List;

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
public class CountryController {
	
	private CountryService countryService;
	
	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}
	
	@GetMapping("/{languageName}/countries")
	public List<Country> findAllCountries(@PathVariable String languageName) {	
		return countryService.findAllCountries(StringFormatter.formatPathVariable(languageName));
	}
	
	@PostMapping("/{languageName}/countries")
	public Country addCountry(@RequestBody Country country) {
		
		countryService.saveCountry(country);
		
		return country;
	}
	
	@PutMapping("/{languageName}/countries")
	public Country updateCountry(@RequestBody Country country) {
		
		countryService.saveCountry(country);
		
		return country;
	}
	
	@DeleteMapping("/{languageName}/{countryName}")
	public String deleteCountry(@PathVariable String languageName, @PathVariable String countryName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		countryService.deleteCountry(languageName, countryName);
		
		return "Country with name '" + countryName + "' for language with name '" + languageName + "' was deleted";
	}
}
