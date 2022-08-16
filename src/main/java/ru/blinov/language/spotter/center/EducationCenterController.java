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

@RestController
@RequestMapping("/api")
public class EducationCenterController {
	
	private EducationCenterService educationCenterService;
	
	@Autowired
	public EducationCenterController(EducationCenterService educationCenterService) {
		this.educationCenterService = educationCenterService;
	}
	
	@GetMapping("/centers")
	public List<EducationCenter> findAllEducationCenters() {
		return educationCenterService.findAllEducationCenters();
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/centers")
	public List<EducationCenter> findAllEducationCenters(@PathVariable String languageName, @PathVariable String countryName,
			@PathVariable String cityName) {
		return educationCenterService.findAllEducationCenters(languageName, countryName, cityName);
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}")
	public EducationCenter findEducationCenter(@PathVariable String languageName, @PathVariable String countryName,
			@PathVariable String cityName, @PathVariable String centerName) {
		return educationCenterService.findEducationCenter(languageName, countryName, cityName, centerName);
	}
	
	@GetMapping("/centers/{centerId}")
	public EducationCenter findEducationCenter(@PathVariable Integer centerId) {
		return educationCenterService.findEducationCenter(centerId);
	}
	
	@PostMapping("/centers")
	public ResponseEntity<Object> addEducationCenter(@Valid @RequestBody EducationCenter center) {
		
		educationCenterService.saveEducationCenter(center);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
                .path("/{centerId}")
                .buildAndExpand(center.getId())
                .toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
	}
	
	@PutMapping("/centers")
	public void updateEducationCenter(@Valid @RequestBody EducationCenter center) {
		educationCenterService.saveEducationCenter(center);
	}
	
	@PostMapping("/centers/{centerId}/languages/{languageId}")
	public void addLanguageToEducationCenter(@PathVariable Integer centerId, @PathVariable Integer languageId) {
		educationCenterService.addLanguageToEducationCenter(centerId, languageId);
	}
	
	@PostMapping("/centers/{centerId}/courses/{courseId}")
	public void addCourseToEducationCenter(@PathVariable Integer centerId, @PathVariable Integer courseId) {
		educationCenterService.addCourseToEducationCenter(centerId, courseId);
	}
	
	@PostMapping("/centers/{centerId}/accommodations/{accommodationId}")
	public void addAccommodationToEducationCenter(@PathVariable Integer centerId, @PathVariable Integer accommodationId) {
		educationCenterService.addAccommodationToEducationCenter(centerId, accommodationId);
	}
	
	@DeleteMapping("/centers/{centerId}")
	public ResponseEntity<Object> deleteEducationCenter(@PathVariable Integer centerId) {
		
		educationCenterService.deleteEducationCenter(centerId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/centers/{centerId}/languages/{languageId}")
	public void removeLanguageFromEducationCenter(@PathVariable Integer centerId, @PathVariable Integer languageId) {
		educationCenterService.removeLanguageFromEducationCenter(centerId, languageId);
	}
	
	@DeleteMapping("/centers/{centerId}/courses/{courseId}")
	public void removeCourseFromEducationCenter(@PathVariable Integer centerId, @PathVariable Integer courseId) {
		educationCenterService.removeCourseFromEducationCenter(centerId, courseId);
	}
	
	@DeleteMapping("/centers/{centerId}/accommodations/{accommodationId}")
	public void removeAccommodationFromEducationCenter(@PathVariable Integer centerId, @PathVariable Integer accommodationId) {
		educationCenterService.removeAccommodationFromEducationCenter(centerId, accommodationId);
	}
}
