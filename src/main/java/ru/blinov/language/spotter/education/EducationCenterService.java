package ru.blinov.language.spotter.education;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EducationCenterService {
	
	private EducationCenterRepository educationCenterRepository;
	
	@Autowired
	public EducationCenterService(EducationCenterRepository educationCenterRepository) {
		this.educationCenterRepository = educationCenterRepository;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllEducationCenters(String cityName, String languageName) {
		return educationCenterRepository.findAllByCityNameAndLanguageName(cityName, languageName);
	}
	
	@Transactional(readOnly = true)
	public EducationCenter findEducationCenter(String languageName, String centerName) {
		
		Optional<EducationCenter> center = educationCenterRepository.findByLanguageNameAndCenterName(languageName, centerName);
		
		if(center.isEmpty()) {
			throw new RuntimeException("Education center with name '" + centerName + "' with language name '" + languageName + "' is not found");
		}
		
		return center.get();
	}
	
	@Transactional
	public void saveEducationCenter(EducationCenter center) {
		educationCenterRepository.save(center);
	}
	
	@Transactional
	public void deleteEducationCenter(String centerName) {
		
		Optional<EducationCenter> center = educationCenterRepository.findByName(centerName);
		
		if(center.isEmpty()) {
			throw new RuntimeException("Education center with name '" + centerName + "' is not found");
		}
		
		educationCenterRepository.deleteByName(centerName);
	}
}
