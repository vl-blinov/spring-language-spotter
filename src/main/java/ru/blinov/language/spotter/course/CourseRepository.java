package ru.blinov.language.spotter.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query(value = "SELECT * FROM course"
			+ " JOIN education_center ON course.education_center_id = education_center.id"
			+ " JOIN language ON course.language_id = language.id"
			+ " WHERE language.name =:languageName"
			+ " AND education_center.name =:centerName", nativeQuery = true)
	public List<Course> findAllByLanguageNameAndCenterName(String languageName, String centerName);
}
