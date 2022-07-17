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

@RestController
@RequestMapping("/api")
public class AccommodationController {
	
	private AccommodationService accommodationService;
	
	@Autowired
	public AccommodationController(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}
	
	@GetMapping("/{centerName}/accommodations")
	public List<Accommodation> getAllAccommodationsFromCenter(@PathVariable String centerName) {
		return accommodationService.findAllAccommodationsByCenterName(centerName);
	}
	
	@PostMapping("/{centerName}/accommodations")
	public Accommodation addAccommodationToCenter(@PathVariable String centerName, @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodationToCenter(centerName, accommodation);
		
		return accommodation;	
	}
	
	@PutMapping("/{centerName}/accommodations")
	public Accommodation updateAccommodationOfCenter(@PathVariable String centerName, @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodationToCenter(centerName, accommodation);
		
		return accommodation;	
	}
	
	@DeleteMapping("/accommodations/{accommodationId}")
	public String deleteAccommodation(@PathVariable int accommodationId) {
		
		accommodationService.deleteAccommodationById( accommodationId);
		
		return "Accommodation with id " + accommodationId + " was deleted";
	}
}
