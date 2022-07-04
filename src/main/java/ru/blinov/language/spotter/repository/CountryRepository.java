package ru.blinov.language.spotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.blinov.language.spotter.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
	
}
