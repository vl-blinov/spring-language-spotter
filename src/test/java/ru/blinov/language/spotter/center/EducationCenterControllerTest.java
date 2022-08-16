package ru.blinov.language.spotter.center;

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

@WebMvcTest(EducationCenterController.class)
public class EducationCenterControllerTest {
	
	@MockBean
	private EducationCenterService educationCenterService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllEducationCenters() throws Exception {
		
		List<EducationCenter> centers = Lists.newArrayList(new EducationCenter(), new EducationCenter());
		
		when(educationCenterService.findAllEducationCenters()).thenReturn(centers);
		
		mockMvc.perform(get("/api/centers"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllEducationCentersByLanguageNameAndCountryNameAndCityName() throws Exception {
		
		List<EducationCenter> centers = Lists.newArrayList(new EducationCenter(), new EducationCenter());
		
		String languageNamePathVariable = "english";
		String countryNamePathVariable = "ireland";
		String cityNamePathVariable = "dublin";
		
		when(educationCenterService.findAllEducationCenters(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable)).thenReturn(centers);
		
		mockMvc.perform(get("/api/{languageName}/{countryName}/{cityName}/centers",
				languageNamePathVariable, countryNamePathVariable, cityNamePathVariable))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindEducationCenterByLanguageNameAndCountryNameAndCityNameAndCenterName() throws Exception {
		
		String languageNamePathVariable = "english";
		String countryNamePathVariable = "ireland";
		String cityNamePathVariable = "dublin";
		String centerNamePathVariable = "erin_school_of_english";
		
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setName("Erin School Of English");
		
		when(educationCenterService.findEducationCenter(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable,
				centerNamePathVariable)).thenReturn(center);
		
		mockMvc.perform(get("/api/{languageName}/{countryName}/{cityName}/{centerName}",
				languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindEducationCenterById() throws Exception {
		
		EducationCenter center = new EducationCenter();
		Integer centerId = 1;
		center.setId(centerId);
		center.setName("Erin School Of English");
		
		when(educationCenterService.findEducationCenter(centerId)).thenReturn(center);
		
		mockMvc.perform(get("/api/centers/{centerId}", centerId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddEducationCenter() throws Exception {
		
		Integer centerId = 1;
		
		mockMvc.perform(post("/api/centers")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + centerId
						+ ", \"name\": \"Erin School Of English\""
						+ ", \"address\": \"43 N Great George's St, Rotunda, Dublin, D01 N6P2, Ireland\""
						+ ", \"registrationFeeAmount\": 85.0"
						+ ", \"registrationFeeCurrency\": \"€\""
						+ ", \"rating\": 4.9}"))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/centers/" + centerId));	
	}
	
	@Test
	public void testUpdateEducationCenter() throws Exception {
		
		Integer centerId = 1;
		
		mockMvc.perform(put("/api/centers")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + centerId
						+ ", \"name\": \"Erin School Of English\""
						+ ", \"address\": \"43 N Great George's St, Rotunda, Dublin, D01 N6P2, Ireland\""
						+ ", \"registrationFeeAmount\": 100.0"
						+ ", \"registrationFeeCurrency\": \"€\""
						+ ", \"rating\": 4.9}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddLanguageToEducationCenter() throws Exception {
		
		Integer centerId = 1;
		Integer languageId = 1;

		mockMvc.perform(post("/api/centers/{centerId}/languages/{languageId}", centerId, languageId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddCourseToEducationCenter() throws Exception {
		
		Integer centerId = 1;
		Integer courseId = 1;

		mockMvc.perform(post("/api/centers/{centerId}/courses/{courseId}", centerId, courseId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddAccommodationToEducationCenter() throws Exception {
		
		Integer centerId = 1;
		Integer accommodationId = 1;

		mockMvc.perform(post("/api/centers/{centerId}/accommodations/{accommodationId}", centerId, accommodationId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteEducationCenter() throws Exception {
		
		Integer centerId = 1;
		
		mockMvc.perform(delete("/api/centers/{centerId}", centerId))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testRemoveLanguageFromEducationCenter() throws Exception {
		
		Integer centerId = 1;
		Integer languageId = 1;

		mockMvc.perform(delete("/api/centers/{centerId}/languages/{languageId}", centerId, languageId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveCourseFromEducationCenter() throws Exception {
		
		Integer centerId = 1;
		Integer courseId = 1;

		mockMvc.perform(delete("/api/centers/{centerId}/courses/{courseId}", centerId, courseId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveAccommodationFromEducationCenter() throws Exception {
		
		Integer centerId = 1;
		Integer accommodationId = 1;

		mockMvc.perform(delete("/api/centers/{centerId}/accommodations/{accommodationId}", centerId, accommodationId))
				.andExpect(status().isOk());
	}
}
