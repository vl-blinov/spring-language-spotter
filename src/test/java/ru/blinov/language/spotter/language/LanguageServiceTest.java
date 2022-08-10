package ru.blinov.language.spotter.language;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.google.common.collect.Lists;

import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;

@ExtendWith(MockitoExtension.class)
public class LanguageServiceTest {
	
	@Mock
	private LanguageRepository languageRepository;
	
	@InjectMocks
	private LanguageService sut;
	
	@Test
	public void Should_get_all_language_entities() {
		
		//Arrange
		List<Language> languages = Lists.newArrayList(new Language(), new Language());
		
		when(languageRepository.findAll()).thenReturn(languages);
		
		int expectedNumberOfLanguages = 2;
		
		//Act
		List<Language> result = sut.findAllLanguages();
		
		//Assert
		assertThat(result).hasSize(expectedNumberOfLanguages);
	}
	
	@Test
	public void Should_get_a_language_entity_by_the_given_id() {
		
		//Arrange
		Language language = new Language();
		Integer languageId = 1;
		language.setId(languageId);
		
		when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
		
		int expectedLanguageId = languageId;

		//Act
		Language result = sut.findLanguage(languageId);
		
		//Assert
		assertThat(result.getId()).isEqualTo(expectedLanguageId);
	}
	
	@Test
	public void When_trying_to_get_a_language_entity_by_the_given_id_then_should_throw_requestUrlException_LANGUAGE_NOT_FOUND() {
		
		//Arrange
		Integer languageId = 999;
		when(languageRepository.findById(languageId)).thenReturn(Optional.empty());
		
		String message = RequestUrlMessage.LANGUAGE_NOT_FOUND.getMessage();
		
		//Assert
		assertThrows(message,
				RequestUrlException.class,
				() -> sut.findLanguage(languageId));
	}
	
	@Test
	public void Should_add_the_given_language_entity() {
		
		//Arrange
		Language language = new Language();
		language.setName("English");
		
		ArgumentCaptor<Language> captor = ArgumentCaptor.forClass(Language.class);

		//Act
		sut.addLanguage(language);
		
		//Assert
		verify(languageRepository).save(captor.capture());
		assertThat(language).isEqualTo(captor.getValue());
	}

	@Test
	public void Should_delete_a_language_entity_by_the_given_id() {
		
		//Arrange
		Integer languageId = 1;
		Language language = new Language();
		language.setId(languageId);
		
		Course courseOne = new Course();
		courseOne.setLanguage(language);
		Course courseTwo = new Course();
		courseTwo.setLanguage(language);
		
		language.setCourses(Lists.newArrayList(courseOne, courseTwo));
		
		when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
		
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		
		//Act
		sut.deleteLanguage(languageId);
		
		//Assert
		assertThat(language.getCourses()).allMatch(c -> c.getLanguage() == null);
		verify(languageRepository).deleteById(captor.capture());
		assertThat(languageId).isEqualTo(captor.getValue());
	}
}
