package ru.blinov.language.spotter.city;

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

import ru.blinov.language.spotter.util.StringFormatter;

@RestController
@RequestMapping("/api")
public class CityController {
	
	private CityService cityService;
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/{languageName}/{countryName}/cities")
	public List<City> findAllCities(@PathVariable String languageName, @PathVariable String countryName) {
		return cityService.findAllCities(StringFormatter.formatPathVariable(countryName), StringFormatter.formatPathVariable(languageName));
	}
	
	@PostMapping("/{languageName}/{countryName}/cities")
	public ResponseEntity<Object> addCity(@PathVariable String languageName, @PathVariable String countryName, @Valid @RequestBody City city) {
		
		cityService.saveCity(languageName, countryName, city);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(city.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/{languageName}/{countryName}/cities")
	public City updateCity(@PathVariable String languageName, @PathVariable String countryName, @Valid @RequestBody City city) {
		
		cityService.saveCity(languageName, countryName, city);
		
		return city;
	}
	
	@DeleteMapping("/{languageName}/{countryName}/{cityName}")
	public ResponseEntity<Object> deleteCity(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		cityService.deleteCity(languageName, countryName, cityName);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
