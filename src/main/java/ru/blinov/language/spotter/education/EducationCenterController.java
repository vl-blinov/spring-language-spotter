package ru.blinov.language.spotter.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EducationCenterController {
	
	private EducationCenterService educationCenterService;
	
	@Autowired
	public EducationCenterController(EducationCenterService educationCenterService) {
		this.educationCenterService = educationCenterService;
	}

	@GetMapping("/{languageName}/centers")
	public List<EducationCenter> getAllCentersOfLanguageToLearn(@PathVariable String languageName) {	
		return educationCenterService.findAllCentersByLanguage(languageName);
	}
	
	@GetMapping("/{languageName}/{countryName}/centers")
	public List<EducationCenter> getAllCentersOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName) {
		return educationCenterService.findAllCentersByLanguageAndCountry(languageName, countryName);
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/centers")
	public List<EducationCenter> getAllCentersOfCityOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName,
																			   @PathVariable String cityName) {
		return educationCenterService.findAllCentersByLanguageAndCountryAndCity(languageName, countryName, cityName);
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}")
	public EducationCenter getCenterOfCityOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName,
			   														 @PathVariable String cityName, @PathVariable String centerName) {
		return educationCenterService.findCenterByLanguageAndCountryAndCityAndName(languageName, countryName, cityName, centerName);
	}
}
