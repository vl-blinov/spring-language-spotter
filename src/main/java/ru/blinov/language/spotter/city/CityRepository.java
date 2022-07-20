package ru.blinov.language.spotter.city;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	@Query(value = "SELECT * FROM city"
				+ " JOIN country ON city.country_id = country.id"
				+ " JOIN language_city ON city.id = language_city.city_id"
				+ " JOIN language ON language_city.language_id = language.id"
				+ " WHERE country.name =:countryName"
				+ " AND language.name =:languageName", nativeQuery = true)
	List<City> findAllByCountryNameAndLanguageName(String countryName, String languageName);

	Optional<City> findByName(String cityName);

	void deleteByName(String cityName);
}
