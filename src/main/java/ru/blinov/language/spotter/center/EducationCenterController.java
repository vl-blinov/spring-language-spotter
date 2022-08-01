package ru.blinov.language.spotter.center;

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
public class EducationCenterController {
	
	private EducationCenterService educationCenterService;
	
	@Autowired
	public EducationCenterController(EducationCenterService educationCenterService) {
		this.educationCenterService = educationCenterService;
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/centers")
	public List<EducationCenter> findAllEducationCenters(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		return educationCenterService.findAllEducationCenters(languageName, countryName, cityName);
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}")
	public EducationCenter findEducationCenter(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
											   @PathVariable String centerName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		return educationCenterService.findEducationCenter(languageName, countryName, cityName, centerName);
	}
	
	@PostMapping("/{languageName}/{countryName}/{cityName}/centers")
	public ResponseEntity<Object> addEducationCenter(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
											  @Valid @RequestBody EducationCenter center) {
		
		educationCenterService.saveEducationCenter(languageName, countryName, cityName, center);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(center.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/{languageName}/{countryName}/{cityName}/centers")
	public EducationCenter updateEducationCenter(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
												 @Valid @RequestBody EducationCenter center) {
		
		educationCenterService.saveEducationCenter(languageName, countryName, cityName, center);
		
		return center;
	}
	
	@DeleteMapping("/{languageName}/{countryName}/{cityName}/{centerName}")
	public ResponseEntity<Object> deleteEducationCenter(@PathVariable String languageName, @PathVariable String countryName, @PathVariable String cityName,
										@PathVariable String centerName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		countryName = StringFormatter.formatPathVariable(countryName);
		
		cityName = StringFormatter.formatPathVariable(cityName);
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		educationCenterService.deleteEducationCenter(languageName, countryName, cityName, centerName);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
