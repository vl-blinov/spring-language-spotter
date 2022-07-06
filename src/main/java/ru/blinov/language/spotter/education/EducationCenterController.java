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
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/centers")
	public List<EducationCenter> getAllCentersForCityAndCountryAndLanguageToLearn(@PathVariable String languageName,
																				  @PathVariable String countryName,
																				  @PathVariable String cityName) {
		return educationCenterService.findAllCentersByCityAndCountryAndLanguageName(languageName, countryName, cityName);
	}
}
