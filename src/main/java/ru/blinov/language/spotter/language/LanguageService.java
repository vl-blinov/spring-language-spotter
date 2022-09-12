package ru.blinov.language.spotter.language;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;

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
	
	@Transactional(readOnly = true)
	public Language findLanguage(Integer languageId) {
		
		Language language = languageRepository.findById(languageId)
				.orElseThrow(() -> new RequestUrlException(RequestUrlMessage.LANGUAGE_NOT_FOUND.getMessage()));
		
		return language;
	}
	
	@Transactional
	public void addLanguage(Language language) {
		languageRepository.save(language);
	}
	
	@Transactional
	public void deleteLanguage(Integer languageId) {
		
		Language language = findLanguage(languageId);

		List<Course> courses = language.getCourses();
		
		courses.forEach(c -> c.setLanguage(null));

		languageRepository.deleteById(languageId);
	}
}
