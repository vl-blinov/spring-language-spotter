package ru.blinov.language.spotter.education;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.country.Country;
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
	public List<EducationCenter> findAllCentersByLanguage(String languageName) {
		
		List<Country> countries = getLanguage(languageName).getCountries();
		
		List<EducationCenter> centers = new LinkedList<>();

		countries.stream().forEach(country -> {
			country.getCities().stream().forEach(city -> {
				city.getEducationCenters().stream().forEach(center -> {
					centers.add(center);
				});
			});
		});
		
		return centers;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllCentersByLanguageAndCountry(String languageName, String countryName) {
		
		List<City> cities = getLanguage(languageName).getCountry(countryName).getCities();
		
		List<EducationCenter> centers = new LinkedList<>();

		cities.stream().forEach(city -> {
			city.getEducationCenters().stream().forEach(center -> {
				centers.add(center);
			});
		});
		
		return centers;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllCentersByLanguageAndCountryAndCity(String languageName, String countryName, String cityName) {
		return getLanguage(languageName).getCountry(countryName).getCity(cityName).getEducationCenters();
	}

	private Language getLanguage(String languageName) {
		return languageRepository.findByName(StringUtils.capitalize(languageName)).get();
	}
	
	@Transactional(readOnly = true)
	public EducationCenter findCenterByLanguageAndCountryAndCityAndName(String languageName, String countryName, String cityName, String centerName) {
		return getLanguage(languageName).getCountry(countryName).getCity(cityName).getEducationCenter(centerName);
	}
}
