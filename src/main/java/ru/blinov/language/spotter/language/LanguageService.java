package ru.blinov.language.spotter.language;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LanguageService {
	
	private LanguageRepository languageRepository;

	@Autowired
	public LanguageService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Language> findAllLanguages() {
		return languageRepository.findAll();
	}
}
