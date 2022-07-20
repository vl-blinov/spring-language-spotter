package ru.blinov.language.spotter.education;

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
public class EducationCenterController {
	
	private EducationCenterService educationCenterService;
	
	@Autowired
	public EducationCenterController(EducationCenterService educationCenterService) {
		this.educationCenterService = educationCenterService;
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/centers")
	public List<EducationCenter> findAllEducationCenters(@PathVariable String languageName, @PathVariable String cityName) {
		return educationCenterService.findAllEducationCenters(StringFormatter.formatPathVariable(cityName), StringFormatter.formatPathVariable(languageName));
	}
	
	@GetMapping("/{languageName}/{countryName}/{cityName}/{centerName}")
	public EducationCenter findEducationCenter(@PathVariable String centerName) {
		return educationCenterService.findEducationCenter(StringFormatter.formatPathVariable(centerName));
	}
	
	@PostMapping("/{languageName}/{countryName}/{cityName}/centers")
	public EducationCenter addEducationCenter(@RequestBody EducationCenter center) {
		
		educationCenterService.saveEducationCenter(center);
		
		return center;
	}
	
	@PutMapping("/{languageName}/{countryName}/{cityName}/centers")
	public EducationCenter updateEducationCenter(@RequestBody EducationCenter center) {
		
		educationCenterService.saveEducationCenter(center);
		
		return center;
	}
	
	@DeleteMapping("/{languageName}/{countryName}/{cityName}/{centerName}")
	public String deleteEducationCenter(@PathVariable String centerName) {
		
		centerName = StringFormatter.formatPathVariable(centerName);
		
		educationCenterService.deleteEducationCenter(centerName);
		
		return "Education center with name " + centerName + " was deleted";
	}
}
