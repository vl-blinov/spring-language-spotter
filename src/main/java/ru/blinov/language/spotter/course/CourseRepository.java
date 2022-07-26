package ru.blinov.language.spotter.course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	@Query(value = "SELECT * FROM course"
				+ " RIGHT JOIN education_center"
				+ " ON course.education_center_id = education_center.id"
				+ " WHERE education_center.name =:centerName", nativeQuery = true)
	public List<Course> findAllByCenterName(String centerName);
	
	@Query(value = "SELECT * FROM course"
				+ " RIGHT JOIN education_center"
				+ " ON course.education_center_id = education_center.id"
				+ " WHERE education_center.name =:centerName"
				+ " LIMIT 1", nativeQuery = true)
	public Optional<Course> findOneByCenterName(String centerName);

	@Query(value = "SELECT * FROM course"
			+ " JOIN education_center ON course.education_center_id = education_center.id"
			+ " JOIN language ON course.language_id = language.id"
			+ " WHERE language.name =:languageName"
			+ " AND education_center.name =:centerName", nativeQuery = true)
	public List<Course> findAllByLanguageNameAndCenterName(String languageName, String centerName);
	
	@Query(value = "SELECT * FROM course"
				+ " JOIN education_center ON course.education_center_id = education_center.id"
				+ " JOIN language ON course.language_id = language.id"
				+ " WHERE language.name =:languageName"
				+ " AND education_center.name IN :centersNames", nativeQuery = true)
	public List<Course> findAllByLanguageNameAndCentersNames(String languageName, List<String> centersNames);
}
