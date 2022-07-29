package ru.blinov.language.spotter.accommodation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
	
	@Query(value = "SELECT * FROM accommodation"
				+ " RIGHT JOIN education_center"
				+ " ON accommodation.education_center_id = education_center.id"
				+ " WHERE education_center.name =:centerName", nativeQuery = true)
	List<Accommodation> findAllByCenterName(String centerName);
}
