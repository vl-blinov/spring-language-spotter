package ru.blinov.language.spotter.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class EducationCenterService {
	
	private EducationCenterRepository educationCenterRepository;
	
	@Autowired
	public EducationCenterService(EducationCenterRepository educationCenterRepository) {
		this.educationCenterRepository = educationCenterRepository;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllCentersByCityAndCountryAndLanguageName(String languageName, String countryName, String cityName) {
		return educationCenterRepository.findAllByCityAndCountryAndLanguageName(StringUtils.capitalize(languageName),
																				StringUtils.capitalize(countryName),
																				StringUtils.capitalize(cityName));
	}

}
