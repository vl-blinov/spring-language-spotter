package ru.blinov.language.spotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.blinov.language.spotter.entity.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
	
}
