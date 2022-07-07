package ru.blinov.language.spotter.accommodation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccommodationController {
	
	private AccommodationService accommodationService;
	
	@Autowired
	public AccommodationController(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	public List<Accommodation> getAllAccommodationsOfCenterOfCityOfCountryOfLanguageToLearn(@PathVariable String languageName, @PathVariable String countryName,
																			  		 		@PathVariable String cityName, @PathVariable String centerName) {
		return accommodationService.findAllAccommodationsByCenterByCityByCountryByLanguageName(languageName, countryName, cityName, centerName);
	}
}
