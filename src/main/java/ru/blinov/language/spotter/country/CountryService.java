package ru.blinov.language.spotter.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CountryService {
	
	private CountryRepository countryRepository;
	
	@Autowired
	public CountryService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Country> findAllCountriesByLanguageName(String languageName) {
		return countryRepository.findAllByLanguageName(StringUtils.capitalize(languageName));
	}
}
