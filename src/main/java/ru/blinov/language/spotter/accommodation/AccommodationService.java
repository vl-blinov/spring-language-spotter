package ru.blinov.language.spotter.accommodation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccommodationService {
	
	private AccommodationRepository accommodationRepository;
	
	@Autowired
	public AccommodationService(AccommodationRepository accommodationRepository) {
		this.accommodationRepository = accommodationRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAllAccommodations(String centerName) {
		
		List<Accommodation> accommodations = accommodationRepository.findAllByCenterName(centerName);
		
		if(accommodations.isEmpty()) {
			throw new RuntimeException("Education center with name '" + centerName + "' does not exist");
		}
		
		if(accommodations.contains(null)) {
			accommodations.clear();
		}
		
		return accommodations;
	}
	
	@Transactional
	public void saveAccommodation(Accommodation accommodation) {
		
		if(accommodation.getId() == 0) {
			
			String centerName = accommodation.getEducationCenter().getName();
			
			Optional<Accommodation> accommodationOfCenter = accommodationRepository.findOneByCenterName(centerName);
			
			if(accommodationOfCenter.isEmpty()) {
				throw new RuntimeException("Education center with name '" + centerName + "' does not exist");
			}
		}
		
		accommodationRepository.save(accommodation);
	}
	
	@Transactional
	public void deleteAccommodation(int accommodationId) {
		
		Optional<Accommodation> accommodation = accommodationRepository.findById(accommodationId);
		
		if(accommodation.isEmpty()) {
			throw new RuntimeException("Accommodation with id " + accommodationId + "is not found");
		}
		
		accommodationRepository.deleteById(accommodationId);
	}
}
