package ru.blinov.language.spotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.blinov.language.spotter.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	
}
