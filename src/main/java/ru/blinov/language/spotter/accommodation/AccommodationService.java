package ru.blinov.language.spotter.accommodation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.util.StringFormatter;
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
	public List<Accommodation> findAllAccommodations() {
		return accommodationRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAllAccommodations(String languageName, String countryName, String cityName, String centerName) {
		
		requestValidator.checkUrlPathVariables(languageName, countryName, cityName, centerName);
		
		return accommodationRepository.findAllByCenterName(StringFormatter.formatPathVariable(centerName));
	}

	@Transactional(readOnly = true)
	public Accommodation findAccommodation(Integer accommodationId) {
		
		Optional<Accommodation> accommodationOptional = accommodationRepository.findById(accommodationId);
		
		if(accommodationOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.ACCOMMODATION_NOT_FOUND.getMessage());
		}
		
		Accommodation accommodation = accommodationOptional.get();
		
		return accommodation;
	}

	@Transactional
	public void saveAccommodation(Accommodation accommodation) {
		accommodationRepository.save(accommodation);
	}
	
	@Transactional
	public void deleteAccommodation(Integer accommodationId) {		
		
		Accommodation accommodation = findAccommodation(accommodationId);

		accommodationRepository.delete(accommodation);
	}
}
