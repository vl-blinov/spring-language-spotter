package ru.blinov.language.spotter.city;

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

@WebMvcTest(CityController.class)
public class CityControllerTest {
	
	@MockBean
	private CityService cityService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllCities() throws Exception {
		
		List<City> cities = Lists.newArrayList(new City(), new City());
		
		when(cityService.findAllCities()).thenReturn(cities);
		
		mockMvc.perform(get("/api/cities"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllCitiesByLanguageNameAndCountryName() throws Exception {
		
		List<City> cities = Lists.newArrayList(new City(), new City());
		
		String languageNamePathVariable = "english";
		String countryNamePathVariable = "ireland";
		
		when(cityService.findAllCities(languageNamePathVariable, countryNamePathVariable)).thenReturn(cities);
		
		mockMvc.perform(get("/api/{languageName}/{countryName}/cities", languageNamePathVariable, countryNamePathVariable))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindCity() throws Exception {
		
		City city = new City();
		Integer cityId = 1;
		city.setId(cityId);
		city.setName("Dublin");
		
		when(cityService.findCity(cityId)).thenReturn(city);
		
		mockMvc.perform(get("/api/cities/{cityId}", cityId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddCity() throws Exception {
		
		Integer cityId = 1;
		
		mockMvc.perform(post("/api/cities")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + cityId
						+ ", \"name\": \"Dublin\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/cities/" + cityId));	
	}
	
	@Test
	public void testAddLanguageToCity() throws Exception {
		
		Integer cityId = 1;
		Integer languageId = 1;

		mockMvc.perform(post("/api/cities/{cityId}/languages/{languageId}", cityId, languageId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddEducationCenterToCity() throws Exception {
		
		Integer cityId = 1;
		Integer centerId = 1;

		mockMvc.perform(post("/api/cities/{cityId}/centers/{centerId}", cityId, centerId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteCity() throws Exception {
		
		Integer cityId = 1;
		
		mockMvc.perform(delete("/api/cities/{cityId}", cityId))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testRemoveLanguageFromCity() throws Exception {
		
		Integer cityId = 1;
		Integer languageId = 1;

		mockMvc.perform(delete("/api/cities/{cityId}/languages/{languageId}", cityId, languageId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveEducationCenterFromCity() throws Exception {
		
		Integer cityId = 1;
		Integer centerId = 1;

		mockMvc.perform(delete("/api/cities/{cityId}/centers/{centerId}", cityId, centerId))
				.andExpect(status().isOk());
	}
}
