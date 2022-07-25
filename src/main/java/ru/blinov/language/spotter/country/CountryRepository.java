package ru.blinov.language.spotter.country;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository extends JpaRepository<Country, Integer> {

	@Query(value = "SELECT * FROM country"
				+ " JOIN language_country ON country.id = language_country.country_id"
				+ " JOIN language ON language_country.language_id = language.id"
				+ " WHERE language.name =:languageName", nativeQuery = true)
	public List<Country> findAllByLanguageName(String languageName);

	@Query(value = "SELECT * FROM country"
				+ " JOIN language_country ON country.id = language_country.country_id"
				+ " JOIN language ON language_country.language_id = language.id"
				+ " WHERE language.name =:languageName"
				+ " AND country.name =:countryName", nativeQuery = true)
	public Optional<Country> findByLanguageNameAndCountryName(String languageName, String countryName);
	
	public Optional<Country> findByName(String countryName);
}
