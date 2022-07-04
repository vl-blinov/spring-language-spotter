package ru.blinov.language.spotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.blinov.language.spotter.entity.EducationCenter;

public interface EducationCenterRepository extends JpaRepository<EducationCenter, Integer> {
	
}
