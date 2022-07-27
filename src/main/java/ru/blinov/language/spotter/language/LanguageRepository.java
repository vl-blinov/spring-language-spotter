package ru.blinov.language.spotter.language;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
	public Optional<Language> findByName(String languageName);
}
