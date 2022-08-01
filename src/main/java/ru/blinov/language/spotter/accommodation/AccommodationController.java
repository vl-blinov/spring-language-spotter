package ru.blinov.language.spotter.accommodation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public List<Accommodation> findAllAccommodations(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
			  										 @PathVariable String centerName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		return accommodationService.findAllAccommodations(languageName, countryName, cityName, centerName);
	}
	
	@PostMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	public Accommodation addAccommodation(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
				 						  @PathVariable String centerName, @Valid @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodation(languageName, countryName, cityName, centerName, accommodation);
		
		return accommodation;	
	}
	
	@PutMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	public Accommodation updateAccommodation(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
											 @PathVariable String centerName, @Valid @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodation(languageName, countryName, cityName, centerName, accommodation);
		
		return accommodation;
	}
	
	@DeleteMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations/{accommodationId}")
	public ResponseEntity<Object> deleteAccommodation(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
									  @PathVariable String centerName, @PathVariable int accommodationId, HttpServletRequest request) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		accommodationService.deleteAccommodation(languageName, countryName, cityName, centerName, accommodationId, request);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
