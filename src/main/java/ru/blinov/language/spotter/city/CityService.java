package ru.blinov.language.spotter.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CityService {
	
	private CityRepository cityRepository;
	
	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCitiesByCountryAndLanguageName(String languageName, String countryName) {
		return cityRepository.findAllByCountryAndLanguageName(StringUtils.capitalize(languageName), StringUtils.capitalize(countryName));
	}
}
