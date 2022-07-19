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
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	public List<Accommodation> findAllAccommodations(@PathVariable String centerName) {
		return accommodationService.findAllAccommodations(StringFormatter.formatPathVariable(centerName));
	}
	
	@PostMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	public Accommodation addAccommodation(@RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodation(accommodation);
		
		return accommodation;	
	}
	
	@PutMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	public Accommodation updateAccommodation(@RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodation(accommodation);
		
		return accommodation;
	}
	
	@DeleteMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations/{accommodationId}")
	public String deleteAccommodation(@PathVariable int accommodationId) {
		
		accommodationService.deleteAccommodation(accommodationId);
		
		return "Accommodation with id " + accommodationId + " was deleted";
	}
}
