package ru.blinov.language.spotter.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;

@Service
public class EducationCenterService {
	
	private LanguageRepository languageRepository;
	
	@Autowired
	public EducationCenterService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllCentersByCityByCountryByLanguageName(String languageName, String countryName, String cityName) {
		return getLanguage(languageName).getCountry(countryName).getCity(cityName).getEducationCenters();
	}

	private Language getLanguage(String languageName) {
		return languageRepository.findByName(StringUtils.capitalize(languageName)).get();
	}
}
