package ru.blinov.language.spotter.center;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EducationCenterRepository extends JpaRepository<EducationCenter, Integer> {
	
	@Query(value = "SELECT * FROM education_center"
				+ " JOIN city ON education_center.city_id = city.id"
				+ " JOIN language_education_center ON education_center.id = language_education_center.education_center_id"
				+ " JOIN language ON language_education_center.language_id = language.id"
				+ " WHERE city.name =:cityName"
				+ " AND language.name =:languageName", nativeQuery = true)
	public List<EducationCenter> findAllByCityNameAndLanguageName(String cityName, String languageName);
	
	@Query(value = "SELECT * FROM education_center"
				+ " JOIN city ON education_center.city_id = city.id"
				+ " JOIN language_education_center ON education_center.id = language_education_center.education_center_id"
				+ " JOIN language ON language_education_center.language_id = language.id"
				+ " WHERE language.name =:languageName"
				+ " AND city.name =:cityName", nativeQuery = true)
	public List<EducationCenter> findAllByLanguageNameAndCityName(String languageName, String cityName);
	
	@Query(value = "SELECT * FROM education_center"
				+ " JOIN city ON education_center.city_id = city.id"
				+ " JOIN language_education_center ON education_center.id = language_education_center.education_center_id"
				+ " JOIN language ON language_education_center.language_id = language.id"
				+ " WHERE language.name =:languageName"
				+ " AND city.name IN :citiesNames", nativeQuery = true)
	public List<EducationCenter> findAllByLanguageNameAndCitiesNames(String languageName, List<String> citiesNames);

	public Optional<EducationCenter> findByName(String centerName);
	
	@Query(value = "SELECT * FROM education_center"
				+ " JOIN language_education_center ON education_center.id = language_education_center.education_center_id"
				+ " JOIN language ON language_education_center.language_id = language.id"
				+ " WHERE language.name =:languageName"
				+ " AND education_center.name =:centerName", nativeQuery = true)
	public Optional<EducationCenter> findByLanguageNameAndCenterName(String languageName, String centerName);

	public void deleteByName(String centerName);
}
