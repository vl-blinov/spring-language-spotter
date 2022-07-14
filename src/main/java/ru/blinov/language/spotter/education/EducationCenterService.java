package ru.blinov.language.spotter.education;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.util.StringFormatter;

@Service
public class EducationCenterService {
	
	private EducationCenterRepository educationCenterRepository;
	
	@Autowired
	public EducationCenterService(EducationCenterRepository educationCenterRepository) {
		this.educationCenterRepository = educationCenterRepository;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllCentersByLanguage(String languageName) {
		return educationCenterRepository.findAll().stream()
				.filter(center -> center.hasLanguage(languageName)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<EducationCenter> findAllCentersByLanguageAndCountry(String languageName, String countryName) {
		return educationCenterRepository.findAll().stream()
				.filter(center -> center.hasLanguage(languageName))
				.filter(center -> center.getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllCentersByLanguageAndCountryAndCity(String languageName, String countryName, String cityName) {
		return educationCenterRepository.findAll().stream()
				.filter(center -> center.hasLanguage(languageName))
				.filter(center -> center.getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.filter(center -> center.getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public EducationCenter findCenterByCountryAndCityAndName(String countryName, String cityName, String centerName) {
		return educationCenterRepository.findAll().stream()
				.filter(center -> center.getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.filter(center -> center.getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(center -> center.getName().equals(StringFormatter.formatPathVariable(centerName)))
				.findAny().get();
	}
}
