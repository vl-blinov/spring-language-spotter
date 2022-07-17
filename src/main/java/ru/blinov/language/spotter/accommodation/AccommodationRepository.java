package ru.blinov.language.spotter.accommodation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
	
	@Query(value = "SELECT * FROM accommodation"
				+ " JOIN education_center"
				+ " ON accommodation.education_center_id = education_center.id"
				+ " WHERE education_center.name =:centerName", nativeQuery = true)
	public List<Accommodation> findAllByCenterName(String centerName);
	
	@Query(value = "SELECT * FROM accommodation"
				+ " JOIN education_center"
				+ " ON accommodation.education_center_id = education_center.id"
				+ " WHERE education_center.name =:centerName"
				+ " LIMIT 1", nativeQuery = true)
	public Optional<Accommodation> findOneByCenterName(String centerName);
}
