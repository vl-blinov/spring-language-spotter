package ru.blinov.language.spotter.city;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Integer> {
	
//	@Query(value="SELECT *"
//			  + " FROM country"
//			  + " LEFT JOIN language_country ON country.id = language_country.country_id"
//			  + " LEFT JOIN language ON language_country.language_id = language.id"
//			  + " WHERE language.name =:languageName", nativeQuery=true)
	
	
	@Query(value="SELECT *"
			  + " FROM city"
			  + " LEFT JOIN country ON country.id = city.country_id"
			  + " LEFT JOIN language_country ON country.id = language_country.country_id"
			  + " LEFT JOIN language ON language_country.language_id = language.id"
			  + " WHERE language.name =:languageName"
			  + " AND country.name =:countryName", nativeQuery=true)
	public List<City> findAllByCountryAndLanguageName(String languageName, String countryName);
	
}
