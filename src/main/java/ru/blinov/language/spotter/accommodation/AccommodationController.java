package ru.blinov.language.spotter.accommodation;

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
public class AccommodationController {
	
	private AccommodationService accommodationService;
	
	@Autowired
	public AccommodationController(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}
	
	@GetMapping("/{countryName}/{cityName}/{centerName}/accommodations")
	public List<Accommodation> getAllAccommodationsOfCenterOfCityOfCountry(@PathVariable String countryName, @PathVariable String cityName,
																		   @PathVariable String centerName) {
		return accommodationService.findAllAccommodationsByCountryAndCityAndCenter(countryName, cityName, centerName);
	}
	
	@PostMapping("/{countryName}/{cityName}/{centerName}/accommodations")
	public Accommodation addAccommodationToCenterOfCityOfCountry(@PathVariable String countryName, @PathVariable String cityName,
			   													 @PathVariable String centerName, @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodationOfCenterOfCityOfCountry(countryName, cityName, centerName, accommodation);
		
		return accommodation;	
	}
	
	@PutMapping("/{countryName}/{cityName}/{centerName}/accommodations")
	public Accommodation updateAccommodationOfCenterOfCityOfCountry(@PathVariable String countryName, @PathVariable String cityName,
			   													 	@PathVariable String centerName, @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodationOfCenterOfCityOfCountry(countryName, cityName, centerName, accommodation);
		
		return accommodation;	
	}
	
	@DeleteMapping("/{countryName}/{cityName}/{centerName}/accommodations/{accommodationId}")
	public String deleteAccommodationOfCenterOfCityOfCountry(@PathVariable String countryName, @PathVariable String cityName,
			 												 @PathVariable String centerName, @PathVariable int accommodationId) {
		
		accommodationService.deleteAccommodationOfCenterOfCityOfCountryById(countryName, cityName, centerName, accommodationId);
		
		return "Accommodation with id " + accommodationId 
			   + " in education center: " + StringFormatter.formatPathVariable(centerName) 
			   + ", city: " + StringFormatter.formatPathVariable(cityName) 
			   + ", country: " + StringFormatter.formatPathVariable(countryName) 
			   + " was deleted";
	}
}
