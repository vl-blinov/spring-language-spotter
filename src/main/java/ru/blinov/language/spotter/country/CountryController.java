package ru.blinov.language.spotter.country;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class CountryController {
	
	private CountryService countryService;
	
	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}
	
	@GetMapping("/countries")
	public List<Country> findAllCountries() {	
		return countryService.findAllCountries();
	}
	
	@GetMapping("/{languageName}/countries")
	public List<Country> findAllCountries(@PathVariable String languageName) {	
		return countryService.findAllCountries(languageName);
	}
	
	@GetMapping("/countries/{countryId}")
	public Country findCountry(@PathVariable Integer countryId) {
		return countryService.findCountry(countryId);
	}
	
	@PostMapping("/countries")
	public ResponseEntity<Object> addCountry(@Valid @RequestBody Country country) {
		
		countryService.addCountry(country);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{countryId}")
                .buildAndExpand(country.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PostMapping("/countries/{countryId}/languages/{languageId}")
	public void addLanguageToCountry(@PathVariable Integer countryId, @PathVariable Integer languageId) {
		countryService.addLanguageToCountry(countryId, languageId);
	}
	
	@PostMapping("/countries/{countryId}/cities/{cityId}")
	public void addCityToCountry(@PathVariable Integer countryId, @PathVariable Integer cityId) {
		countryService.addCityToCountry(countryId, cityId);
	}

	@DeleteMapping("/countries/{countryId}")
	public ResponseEntity<Object> deleteCountry(@PathVariable Integer countryId) {
		
		countryService.deleteCountry(countryId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/countries/{countryId}/languages/{languageId}")
	public void removeLanguageFromCountry(@PathVariable Integer countryId, @PathVariable Integer languageId) {
		countryService.removeLanguageFromCountry(countryId, languageId);
	}
	
	@DeleteMapping("/countries/{countryId}/cities/{cityId}")
	public void removeCityFromCountry(@PathVariable Integer countryId, @PathVariable Integer cityId) {
		countryService.removeCityFromCountry(countryId, cityId);
	}
}
