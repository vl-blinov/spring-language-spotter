package ru.blinov.language.spotter.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;

@Service
public class CountryService {
	
	private LanguageRepository languageRepository;
	
	@Autowired
	public CountryService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Country> findAllCountriesByLanguageName(String languageName) {
		return getLanguage(languageName).getCountries();
	}

	private Language getLanguage(String languageName) {
		return languageRepository.findByName(StringUtils.capitalize(languageName)).get();
	}
}
