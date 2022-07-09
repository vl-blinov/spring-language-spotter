package ru.blinov.language.spotter.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;

@Service
public class CityService {
	
	private LanguageRepository languageRepository;
	
	@Autowired
	public CityService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCitiesByCountryAndLanguageName(String languageName, String countryName) {
		return getLanguage(languageName).getCountry(countryName).getCities();
	}

	private Language getLanguage(String languageName) {
		return languageRepository.findByName(StringUtils.capitalize(languageName)).get();
	}
}
