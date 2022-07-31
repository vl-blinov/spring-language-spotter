package ru.blinov.language.spotter.accommodation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class AccommodationService {
	
	private AccommodationRepository accommodationRepository;
	
	private RequestValidator requestValidator;
	
	@Autowired
	public AccommodationService(AccommodationRepository accommodationRepository, RequestValidator requestValidator) {
		
		this.accommodationRepository = accommodationRepository;
		this.requestValidator = requestValidator;
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAllAccommodations(String languageName, String countryName, String cityName, String centerName) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName, centerName);
		
		return accommodationRepository.findAllByCenterName(centerName);
	}

	@Transactional
	public void saveAccommodation(Accommodation accommodation) {
		accommodationRepository.save(accommodation);
	}
	
	@Transactional
	public void deleteAccommodation(String languageName, String countryName, String cityName, String centerName, int accommodationId, HttpServletRequest request) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName, centerName, accommodationId, request);
		
		accommodationRepository.deleteById(accommodationId);
	}
}
