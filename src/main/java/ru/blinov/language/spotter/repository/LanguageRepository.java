package ru.blinov.language.spotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.blinov.language.spotter.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
	
}
