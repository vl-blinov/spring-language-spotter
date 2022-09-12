package ru.blinov.language.spotter.accommodation;

import java.util.List;

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
	public List<Accommodation> findAllAccommodations(String languageNamePathVariable, String countryNamePathVariable, String cityNamePathVariable,
			String centerNamePathVariable) {
		
		requestValidator.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable);
		
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		
		return accommodationRepository.findAllByCenterName(centerName);
	}

	@Transactional(readOnly = true)
	public Accommodation findAccommodation(Integer accommodationId) {
		
		Accommodation accommodation = accommodationRepository.findById(accommodationId)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.ACCOMMODATION_NOT_FOUND.getMessage()));
		
		return accommodation;
	}

	@Transactional
	public void saveAccommodation(Accommodation accommodation) {
		accommodationRepository.save(accommodation);
	}
	
	@Transactional
	public void deleteAccommodation(Integer accommodationId) {		
		
		findAccommodation(accommodationId);

		accommodationRepository.deleteById(accommodationId);
	}
}
