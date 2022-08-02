package ru.blinov.language.spotter.language;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.course.Course;
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
		
		Optional<Language> languageOptional = languageRepository.findById(languageId);
		
		if(languageOptional.isEmpty()) {
			throw new RequestUrlException("Language with id '" + languageId + "' is not found");
		}
		
		Language language = languageOptional.get();
		
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
		
		courses.forEach(c -> {	
			if(c.getLanguage().getId() == languageId) {
				c.setLanguage(null);
			}
		});

		languageRepository.delete(language);	
	}
}
