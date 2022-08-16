package ru.blinov.language.spotter.course;

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

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
	
	@MockBean
	private CourseService courseService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllCourses() throws Exception {
		
		List<Course> courses = Lists.newArrayList(new Course(), new Course());
		
		when(courseService.findAllCourses()).thenReturn(courses);
		
		mockMvc.perform(get("/api/courses"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllCoursesByLanguageNameAndCountryNameAndCityNameAndCenterName() throws Exception {
		
		List<Course> courses = Lists.newArrayList(new Course(), new Course());
		
		String languageNamePathVariable = "english";
		String countryNamePathVariable = "ireland";
		String cityNamePathVariable = "dublin";
		String centerNamePathVariable = "erin_school_of_english";
		
		when(courseService.findAllCourses(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable))
		.thenReturn(courses);
		
		mockMvc.perform(get("/api/{languageName}/{countryName}/{cityName}/{centerName}/courses",
				languageNamePathVariable, countryNamePathVariable, cityNamePathVariable, centerNamePathVariable))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFindCourse() throws Exception {
		
		Course course = new Course();
		Integer courseId = 1;
		course.setId(courseId);
		course.setCourseType("General English");
		
		when(courseService.findCourse(courseId)).thenReturn(course);
		
		mockMvc.perform(get("/api/courses/{courseId}", courseId))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAddCourse() throws Exception {
		
		Integer courseId = 1;
		
		mockMvc.perform(post("/api/courses")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + courseId
						+ ", \"courseType\": \"General English\""
						+ ", \"studentsPerClass\": 15"
						+ ", \"courseDuration\": \"1 - 12 weeks\""
						+ ", \"lessonsPerWeek\": 15"
						+ ", \"classTime\": \"Monday - Friday 09:00 - 12:15\""
						+ ", \"lessonDuration\": \"60 minutes\""
						+ ", \"ageRestriction\": \"16+ years old\""
						+ ", \"entryLevel\": \"Elementary (A1)\""
						+ ", \"pricePerWeekAmount\": 200.0"
						+ ", \"pricePerWeekCurrency\": \"€\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/courses/" + courseId));	
	}
	
	@Test
	public void testUpdateCourse() throws Exception {
		
		Integer courseId = 1;
		
		mockMvc.perform(put("/api/courses")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":" + courseId
						+ ", \"courseType\": \"General English\""
						+ ", \"studentsPerClass\": 15"
						+ ", \"courseDuration\": \"1 - 12 weeks\""
						+ ", \"lessonsPerWeek\": 15"
						+ ", \"classTime\": \"Monday - Friday 09:00 - 12:15\""
						+ ", \"lessonDuration\": \"60 minutes\""
						+ ", \"ageRestriction\": \"16+ years old\""
						+ ", \"entryLevel\": \"Elementary (A1)\""
						+ ", \"pricePerWeekAmount\": 180.0"
						+ ", \"pricePerWeekCurrency\": \"€\"}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteCourse() throws Exception {
		
		Integer courseId = 1;
		
		mockMvc.perform(delete("/api/courses/{courseId}", courseId))
				.andExpect(status().isNoContent());
	}
}
