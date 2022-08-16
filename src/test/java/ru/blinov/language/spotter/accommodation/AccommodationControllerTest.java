package ru.blinov.language.spotter.accommodation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

@WebMvcTest(AccommodationController.class)
public class AccommodationControllerTest {
	
	@MockBean
	private AccommodationService accommodationService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllAccommodations() throws Exception {
		
		List<Accommodation> accommodations = Lists.newArrayList(new Accommodation(), new Accommodation());
		
		when(accommodationService.findAllAccommodations()).thenReturn(accommodations);
		
		mockMvc.perform(get("/api/accommodations"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllAccommodationsByLanguageNameAndCountryNameAndCityNameAndCenterName() throws Exception {
		
		List<Accommodation> accommodations = Lists.newArrayList(new Accommodation(), new Accommodation());
		
		String languageNamePathVariable = "english";
		String countryNamePathVariable = "ireland";
		String cityNamePathVariable = "dublin";
		String centerNamePathVariable = "erin_school_of_english";
		
		when(accommodationService.findAllAccommodations(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable))
		.thenReturn(accommodations);
		
		mockMvc.perform(get("/api/{languageName}/{countryName}/{cityName}/{centerName}/accommodations",
				languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAccommodation() throws Exception {
		
		Accommodation accommodation = new Accommodation();
		Integer accommodationId = 1;
		accommodation.setId(accommodationId);
		accommodation.setAccommodationType("Hostel");
		
		when(accommodationService.findAccommodation(accommodationId)).thenReturn(accommodation);
		
		mockMvc.perform(get("/api/accommodations/{accommodationId}", accommodationId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddAccommodation() throws Exception {
		
		Integer accommodationId = 1;
		
		mockMvc.perform(post("/api/accommodations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + accommodationId
						+ ", \"accommodationType\": \"Homestay\""
						+ ", \"roomType\": \"Single room\""
						+ ", \"mealIncludedType\": \"Half board\""
						+ ", \"ageRestriction\": \"16+ years old\""
						+ ", \"pricePerWeekAmount\": 260.0"
						+ ", \"pricePerWeekCurrency\": \"€\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/accommodations/" + accommodationId));	
	}
	
	@Test
	public void testUpdateAccommodation() throws Exception {
		
		Integer accommodationId = 1;
		
		mockMvc.perform(put("/api/accommodations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + accommodationId
						+ ", \"accommodationType\": \"Homestay\""
						+ ", \"roomType\": \"Single room\""
						+ ", \"mealIncludedType\": \"Half board\""
						+ ", \"ageRestriction\": \"16+ years old\""
						+ ", \"pricePerWeekAmount\": 230.0"
						+ ", \"pricePerWeekCurrency\": \"€\"}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteAccommodation() throws Exception {
		
		Integer accommodationId = 1;
		
		mockMvc.perform(delete("/api/accommodations/{accommodationId}", accommodationId))
				.andExpect(status().isNoContent());
	}
}
