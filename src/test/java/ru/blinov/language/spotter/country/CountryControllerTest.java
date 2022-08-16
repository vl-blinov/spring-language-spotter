package ru.blinov.language.spotter.country;

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

@WebMvcTest(CountryController.class)
public class CountryControllerTest {
	
	@MockBean
	private CountryService countryService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllCountries() throws Exception {
		
		List<Country> countries = Lists.newArrayList(new Country(), new Country());
		
		when(countryService.findAllCountries()).thenReturn(countries);
		
		mockMvc.perform(get("/api/countries"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllCountriesByLanguageName() throws Exception {
		
		List<Country> countries = Lists.newArrayList(new Country(), new Country());
		String languageNamePathVariable = "english";
		
		when(countryService.findAllCountries(languageNamePathVariable)).thenReturn(countries);
		
		mockMvc.perform(get("/api/{languageName}/countries", languageNamePathVariable))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindCountry() throws Exception {
		
		Country country = new Country();
		Integer countryId = 1;
		country.setId(countryId);
		country.setName("Ireland");
		
		when(countryService.findCountry(countryId)).thenReturn(country);
		
		mockMvc.perform(get("/api/countries/{countryId}", countryId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddCountry() throws Exception {
		
		Integer countryId = 1;
		
		mockMvc.perform(post("/api/countries")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + countryId
						+ ", \"name\": \"Ireland\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/countries/" + countryId));	
	}
	
	@Test
	public void testAddLanguageToCountry() throws Exception {
		
		Integer countryId = 1;
		Integer languageId = 1;

		mockMvc.perform(post("/api/countries/{countryId}/languages/{languageId}", countryId, languageId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddCityToCountry() throws Exception {
		
		Integer countryId = 1;
		Integer cityId = 1;
		
		mockMvc.perform(post("/api/countries/{countryId}/cities/{cityId}", countryId, cityId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteCountry() throws Exception {
		
		Integer countryId = 1;
		
		mockMvc.perform(delete("/api/countries/{countryId}", countryId))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testRemoveLanguageFromCountry() throws Exception {
		
		Integer countryId = 1;
		Integer languageId = 1;

		mockMvc.perform(delete("/api/countries/{countryId}/languages/{languageId}", countryId, languageId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveCityFromCountry() throws Exception {
		
		Integer countryId = 1;
		Integer cityId = 1;
		
		mockMvc.perform(delete("/api/countries/{countryId}/cities/{cityId}", countryId, cityId))
				.andExpect(status().isOk());
	}
}
