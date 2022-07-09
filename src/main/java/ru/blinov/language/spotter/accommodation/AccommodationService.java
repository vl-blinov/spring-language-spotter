package ru.blinov.language.spotter.accommodation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageRepository;

@Service
public class AccommodationService {
	
	private LanguageRepository languageRepository;
	
	@Autowired
	public AccommodationService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAllAccommodationsByCenterAndCityAndCountryAndLanguageName(String languageName, String countryName, String cityName, String centerName) {	
		return getLanguage(languageName).getCountry(countryName).getCity(cityName).getEducationCenter(centerName).getAccommodations();
	}
	
	private Language getLanguage(String languageName) {
		return languageRepository.findByName(StringUtils.capitalize(languageName)).get();
	}
}
