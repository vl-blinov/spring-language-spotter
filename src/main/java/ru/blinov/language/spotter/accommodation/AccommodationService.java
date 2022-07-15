package ru.blinov.language.spotter.accommodation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public List<Accommodation> findAllAccommodationsByCountryAndCityAndCenter(String countryName, String cityName, String centerName) {	
		return accommodationRepository.findAll().stream()
				.filter(accommodation -> accommodation.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.filter(accommodation -> accommodation.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(accommodation -> accommodation.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void saveAccommodationOfCenterOfCityOfCountry(String countryName, String cityName, String centerName, Accommodation accommodation) {

		Optional<Accommodation> filteredAccommodation = accommodationRepository.findAll().stream()
				.filter(a -> a.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.filter(a -> a.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(a -> a.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.findAny();
		
		if(filteredAccommodation.isEmpty()) {
			throw new RuntimeException("There are no such country/city/center");
		}
		
		accommodation.setEducationCenter(filteredAccommodation.get().getEducationCenter());
		
		accommodationRepository.save(accommodation);
	}

	public void deleteAccommodationOfCenterOfCityOfCountryById(String countryName, String cityName, String centerName, int accommodationId) {
		
		List<Accommodation> filteredAccommodations = accommodationRepository.findAll().stream()
				.filter(a -> a.getEducationCenter().getName().equals(StringFormatter.formatPathVariable(centerName)))
				.filter(a -> a.getEducationCenter().getCity().getName().equals(StringFormatter.formatPathVariable(cityName)))
				.filter(a -> a.getEducationCenter().getCity().getCountry().getName().equals(StringFormatter.formatPathVariable(countryName)))
				.collect(Collectors.toList());
		
		if(filteredAccommodations.isEmpty()) {
			throw new RuntimeException("There are no such country/city/center");
		}
		
		Optional<Accommodation> accommodation = filteredAccommodations.stream()
				.filter(a -> a.getId() == accommodationId)
				.findAny();
		
		if(accommodation.isEmpty()) {
			throw new RuntimeException("Accommodation with id " + accommodationId 
									   + " in education center: " + StringFormatter.formatPathVariable(centerName) 
									   + ", city: " + StringFormatter.formatPathVariable(cityName) 
									   + ", country: " + StringFormatter.formatPathVariable(countryName) 
									   + " does not exist");
		}
		
		accommodationRepository.deleteById(accommodationId);
	}
}
