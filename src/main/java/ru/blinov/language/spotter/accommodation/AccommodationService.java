package ru.blinov.language.spotter.accommodation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.validator.UrlValidator;

@Service
public class AccommodationService {
	
	private AccommodationRepository accommodationRepository;
	
	private UrlValidator urlValidator;
	
	@Autowired
	public AccommodationService(AccommodationRepository accommodationRepository, UrlValidator urlValidator) {
		
		this.accommodationRepository = accommodationRepository;
		this.urlValidator = urlValidator;
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAllAccommodations(String languageName, String countryName, String cityName, String centerName) {
		
		urlValidator.checkLanguageAndCountryAndCityAndCenter(languageName, countryName, cityName, centerName);
		
		return accommodationRepository.findAllByCenterName(centerName);
	}

	@Transactional
	public void saveAccommodation(Accommodation accommodation) {
		accommodationRepository.save(accommodation);
	}
	
	@Transactional
	public void deleteAccommodation(String languageName, String countryName, String cityName, String centerName, int accommodationId) {
		
		urlValidator.checkLanguageAndCountryAndCityAndCenterAndAccommodation(languageName, countryName, cityName, centerName, accommodationId);
		
		accommodationRepository.deleteById(accommodationId);
	}
}
