package ru.blinov.language.spotter.city;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.util.StringFormatter;

@Service
public class CityService {
	
	private CityRepository cityRepository;
	
	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCitiesByCountry(String countryName) {
		return cityRepository.findAll().stream()
				.filter(city -> city.getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCitiesByLanguageAndCountry(String languageName, String countryName) {
		return cityRepository.findAll().stream()
				.filter(city -> city.hasLanguage(languageName))
				.filter(city -> city.getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.collect(Collectors.toList());
	}
}
