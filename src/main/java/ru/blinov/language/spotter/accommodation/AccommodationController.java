package ru.blinov.language.spotter.accommodation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class AccommodationController {
	
	private AccommodationService accommodationService;
	
	@Autowired
	public AccommodationController(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}
	
	@GetMapping("/accommodations")
	public List<Accommodation> findAllAccommodations() {
		return accommodationService.findAllAccommodations();
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}/accommodations")
	public List<Accommodation> findAllAccommodations(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
			@PathVariable String centerName) {
		return accommodationService.findAllAccommodations(languageName, countryName, cityName, centerName);
	}
	
	@GetMapping("/accommodations/{accommodationId}")
	public Accommodation findAccommodation(@PathVariable Integer accommodationId) {
		return accommodationService.findAccommodation(accommodationId);
	}
	
	@PostMapping("/accommodations")
	public ResponseEntity<Object> addAccommodation(@Valid @RequestBody Accommodation accommodation) {
		
		accommodationService.saveAccommodation(accommodation);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accommodation.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();	
	}
	
	@PutMapping("/accommodations")
	public void updateAccommodation(@Valid @RequestBody Accommodation accommodation) {
		accommodationService.saveAccommodation(accommodation);
	}
	
	@DeleteMapping("/accommodations/{accommodationId}")
	public ResponseEntity<Object> deleteAccommodation(@PathVariable Integer accommodationId) {
		
		accommodationService.deleteAccommodation(accommodationId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
