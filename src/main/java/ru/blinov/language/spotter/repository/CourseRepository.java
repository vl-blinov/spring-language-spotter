package ru.blinov.language.spotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.blinov.language.spotter.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
}
