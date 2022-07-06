package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	@Query(value="SELECT *"
			  + " FROM course"
			  + " LEFT JOIN education_center ON education_center.id = course.education_center_id"
			  + " LEFT JOIN city ON city.id = education_center.city_id"
			  + " LEFT JOIN country ON country.id = city.country_id"
			  + " LEFT JOIN language_country ON country.id = language_country.country_id"
			  + " LEFT JOIN language ON language_country.language_id = language.id"
			  + " WHERE language.name =:languageName"
			  + " AND country.name =:countryName"
			  + " AND city.name =:cityName"
			  + " AND education_center.name =:centerName", nativeQuery=true)
	public List<Course> findAllByCenterAndCityAndCountryAndLanguageName(String languageName, String countryName, String cityName, String centerName);
}
