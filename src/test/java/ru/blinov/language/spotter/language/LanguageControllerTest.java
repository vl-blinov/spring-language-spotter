package ru.blinov.language.spotter.language;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LanguageController.class)
public class LanguageControllerTest {
	
	@MockBean
	private LanguageService languageService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFindAllLanguages() throws Exception {
		
		List<Language> languages = Lists.newArrayList(new Language(), new Language());
		
		when(languageService.findAllLanguages()).thenReturn(languages);
		
		mockMvc.perform(get("/api/languages"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindLanguage() throws Exception {
		
		Language language = new Language();
		Integer languageId = 1;
		language.setId(languageId);
		language.setName("English");
		
		when(languageService.findLanguage(languageId)).thenReturn(language);
		
		mockMvc.perform(get("/api/languages/{languageId}", languageId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddLanguage() throws Exception {
		
		Integer languageId = 1;
		
		mockMvc.perform(post("/api/languages")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + languageId
						+ ", \"name\": \"English\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/languages/" + languageId));
	}
	
	@Test
	public void testDeleteLanguage() throws Exception {
		
		Integer languageId = 1;
		
		mockMvc.perform(delete("/api/languages/{languageId}", languageId))
				.andExpect(status().isNoContent());
	}
}
