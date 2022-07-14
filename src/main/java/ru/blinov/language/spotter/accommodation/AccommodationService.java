package ru.blinov.language.spotter.accommodation;

import java.util.List;
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
}
