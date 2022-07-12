package ru.blinov.language.spotter.city;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;
import ru.blinov.language.spotter.util.StringFormatter;

@Service
public class CityService {
	
	private LanguageRepository languageRepository;
	
	@Autowired
	public CityService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCitiesByLanguageAndCountry(String languageName, String countryName) {
		return getLanguage(languageName).getCountry(countryName).getCities()
				.stream().filter(city -> city.hasLanguage(languageName)).collect(Collectors.toList());
	}

	private Language getLanguage(String languageName) {
		return languageRepository.findByName(StringUtils.capitalize(languageName)).get();
	}
}
