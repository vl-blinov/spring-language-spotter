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
				+ " WHERE language.name =:languageName"
				+ " AND country.name =:countryName", nativeQuery = true)
	public List<City> findAllByLanguageNameAndCountryName(String languageName, String countryName);
	
	@Query(value = "SELECT * FROM city"
				+ " JOIN country ON city.country_id = country.id"
				+ " JOIN language_city ON city.id = language_city.city_id"
				+ " JOIN language ON language_city.language_id = language.id"
				+ " WHERE language.name =:languageName"
				+ " AND country.name IN :countriesNames", nativeQuery = true)
	public List<City> findAllByLanguageNameAndCountriesNames(String languageName, List<String> countriesNames);

	public Optional<City> findByName(String cityName);

	public void deleteByName(String cityName);
}
