package ru.blinov.language.spotter.education;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EducationCenterRepository extends JpaRepository<EducationCenter, Integer> {
	
	@Query(value="SELECT *"
			  + " FROM education_center"
			  + " LEFT JOIN city ON city.id = education_center.city_id"
			  + " LEFT JOIN country ON country.id = city.country_id"
			  + " LEFT JOIN language_country ON country.id = language_country.country_id"
			  + " LEFT JOIN language ON language_country.language_id = language.id"
			  + " WHERE language.name =:languageName"
			  + " AND country.name =:countryName"
			  + " AND city.name =:cityName", nativeQuery=true)
	public List<EducationCenter> findAllByCityAndCountryAndLanguageName(String languageName, String countryName, String cityName);
	
}
