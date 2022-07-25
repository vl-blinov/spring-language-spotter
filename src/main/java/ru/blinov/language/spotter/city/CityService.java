package ru.blinov.language.spotter.city;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService {
	
	private CityRepository cityRepository;
	
	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	@Transactional(readOnly = true)
	public List<City> findAllCities(String countryName, String languageName) {
		return cityRepository.findAllByLanguageNameAndCountryName(languageName, countryName);
	}
	
	@Transactional
	public void saveCity(City city) {
		cityRepository.save(city);
	}
	
	@Transactional
	public void deleteCity(String cityName) {
		
		Optional<City> city = cityRepository.findByName(cityName);
		
		if(city.isEmpty()) {
			throw new RuntimeException("City with name '" + cityName + "' is not found");
		}
		
		cityRepository.deleteByName(cityName);
	}
}
