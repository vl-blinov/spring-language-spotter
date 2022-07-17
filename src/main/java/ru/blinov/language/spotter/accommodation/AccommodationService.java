package ru.blinov.language.spotter.accommodation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.util.StringFormatter;

@Service
public class AccommodationService {
	
	private AccommodationRepository accommodationRepository;
	
	@Autowired
	public AccommodationService(AccommodationRepository accommodationRepository) {
		this.accommodationRepository = accommodationRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAllAccommodationsByCenterName(String centerName) {
		return accommodationRepository.findAllByCenterName(StringFormatter.formatPathVariable(centerName));
	}
	
	@Transactional
	public void saveAccommodationToCenter(String centerName, Accommodation accommodation) {

		Optional<Accommodation> accommodationOfCenter = accommodationRepository.findOneByCenterName(StringFormatter.formatPathVariable(centerName));
		
		if(accommodationOfCenter.isEmpty()) {
			throw new RuntimeException("Education center with name '" + StringFormatter.formatPathVariable(centerName) + "' is not found");
		}
		
		accommodation.setEducationCenter(accommodationOfCenter.get().getEducationCenter());
		
		accommodationRepository.save(accommodation);
	}
	
	@Transactional
	public void deleteAccommodationById(int accommodationId) {
		
		Optional<Accommodation> accommodation = accommodationRepository.findById(accommodationId);
		
		if(accommodation.isEmpty()) {
			throw new RuntimeException("Accommodation with id " + accommodationId + "is not found");
		}
		
		accommodationRepository.deleteById(accommodationId);
	}
}
