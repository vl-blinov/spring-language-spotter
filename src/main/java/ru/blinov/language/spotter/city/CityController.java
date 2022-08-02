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

@RestController
@RequestMapping("/api")
public class CityController {
	
	private CityService cityService;
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/cities")
	public List<City> findAllCities() {
		return cityService.findAllCities();
	}
	
	@GetMapping("/{languageName}/{countryName}/cities")
	public List<City> findAllCities(@PathVariable String languageName, @PathVariable String countryName) {
		return cityService.findAllCities(languageName, countryName);
	}
	
	@GetMapping("/cities/{cityId}")
	public City findCity(@PathVariable Integer cityId) {
		return cityService.findCity(cityId);
	}
	
	@PostMapping("/cities")
	public ResponseEntity<Object> addCity(@Valid @RequestBody City city) {
		
		cityService.addCity(city);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{cityId}")
                .buildAndExpand(city.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/cities/{cityId}/languages/{languageId}")
	public void addLanguageToCity(@PathVariable Integer cityId, @PathVariable Integer languageId) {
		cityService.addLanguageToCity(cityId, languageId);
	}
	
	@PutMapping("/cities/{cityId}/centers/{centerId}")
	public void addEducationCenterToCity(@PathVariable Integer cityId, @PathVariable Integer centerId) {
		cityService.addEducationCenterToCity(cityId, centerId);
	}
	
	@DeleteMapping("/cities/{cityId}")
	public ResponseEntity<Object> deleteCity(@PathVariable Integer cityId) {
		
		cityService.deleteCity(cityId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/cities/{cityId}/languages/{languageId}")
	public void removeLanguageFromCity(@PathVariable Integer cityId, @PathVariable Integer languageId) {
		cityService.removeLanguageFromCity(cityId, languageId);
	}
	
	@DeleteMapping("/cities/{cityId}/centers/{centerId}")
	public void removeEducationCenterFromCity(@PathVariable Integer cityId, @PathVariable Integer centerId) {
		cityService.removeEducationCenterFromCity(cityId, centerId);
	}
}
