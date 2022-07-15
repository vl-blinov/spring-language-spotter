package ru.blinov.language.spotter.language;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.util.StringFormatter;

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
	
	public void addLanguage(Language language) {
		
		language.setId(0);
		
		languageRepository.save(language);
	}
	
	@Transactional
	public void deleteLanguageByName(String languageName) {
		
		languageName = StringFormatter.formatPathVariable(languageName);
		
		Optional<Language> language = languageRepository.findByName(languageName);
		
		if(language.isEmpty()) {
			throw new RuntimeException("Language with name '" + languageName + "' does not exist");
		}
		
		languageRepository.deleteByName(languageName);
	}
}
