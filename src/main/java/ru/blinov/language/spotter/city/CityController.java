package ru.blinov.language.spotter.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CityController {
	
	private CityService cityService;
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/{languageName}/{countryName}/cities")
	public List<City> getAllCitiesForCountryAndLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName) {
		return cityService.findAllCitiesByCountryAndLanguageName(languageName, countryName);
	}
}
