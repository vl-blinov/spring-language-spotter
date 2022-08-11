package ru.blinov.language.spotter.center;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.blinov.language.spotter.accommodation.Accommodation;
import ru.blinov.language.spotter.accommodation.AccommodationService;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.course.CourseService;
import ru.blinov.language.spotter.enums.RequestUrlMessage;
import ru.blinov.language.spotter.exception.RequestUrlException;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.language.LanguageService;
import ru.blinov.language.spotter.util.StringFormatter;
import ru.blinov.language.spotter.validator.RequestValidator;

@Service
public class EducationCenterService {
	
	private LanguageService languageService;
	
	private CourseService courseService;
	
	private AccommodationService accommodationService;
	
	private EducationCenterRepository educationCenterRepository;
	
	private RequestValidator requestValidator;

	@Autowired
	public EducationCenterService(LanguageService languageService, CourseService courseService,
			AccommodationService accommodationService, EducationCenterRepository educationCenterRepository,
			RequestValidator requestValidator) {
		this.languageService = languageService;
		this.courseService = courseService;
		this.accommodationService = accommodationService;
		this.educationCenterRepository = educationCenterRepository;
		this.requestValidator = requestValidator;
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllEducationCenters() {
		return educationCenterRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<EducationCenter> findAllEducationCenters(String languageNamePathVariable, String countryNamePathVariable, String cityNamePathVariable) {
		
		requestValidator.checkUrlPathVariables(languageNamePathVariable, countryNamePathVariable, cityNamePathVariable);
		
		String languageName = StringFormatter.formatPathVariable(languageNamePathVariable);
		
		String cityName = StringFormatter.formatPathVariable(cityNamePathVariable);
		
		return educationCenterRepository.findAllByLanguageNameAndCityName(languageName, cityName);
	}

	@Transactional(readOnly = true)
	public EducationCenter findEducationCenter(String centerNamePathVariable) {
		
		String centerName = StringFormatter.formatPathVariable(centerNamePathVariable);
		
		Optional<EducationCenter> centerOptional = educationCenterRepository.findByName(centerName);
		
		if(centerOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage());
		}
		
		EducationCenter center = centerOptional.get();
		
		return center;
	}
	
	@Transactional(readOnly = true)
	public EducationCenter findEducationCenter(Integer centerId) {
		
		Optional<EducationCenter> centerOptional = educationCenterRepository.findById(centerId);
		
		if(centerOptional.isEmpty()) {
			throw new RequestUrlException(RequestUrlMessage.EDUCATION_CENTER_NOT_FOUND.getMessage());
		}
		
		EducationCenter center = centerOptional.get();
		
		return center;
	}
	
	@Transactional
	public void saveEducationCenter(EducationCenter center) {
		educationCenterRepository.save(center);
	}
	
	@Transactional
	public void addLanguageToEducationCenter(Integer centerId, Integer languageId) {

		EducationCenter center = findEducationCenter(centerId);
		
		Language language = languageService.findLanguage(languageId);
		
		center.addLanguage(language);
	}

	@Transactional
	public void addCourseToEducationCenter(Integer centerId, Integer courseId) {
		
		EducationCenter center = findEducationCenter(centerId);
		
		Course course = courseService.findCourse(courseId);
		
		center.addCourse(course);	
	}

	@Transactional
	public void addAccommodationToEducationCenter(Integer centerId, Integer accommodationId) {
		
		EducationCenter center = findEducationCenter(centerId);
		
		Accommodation accommodation = accommodationService.findAccommodation(accommodationId);
		
		center.addAccommodation(accommodation);
	}
	
	@Transactional
	public void deleteEducationCenter(Integer centerId) {
		
		findEducationCenter(centerId);
		
		educationCenterRepository.deleteById(centerId);	
	}

	@Transactional
	public void removeLanguageFromEducationCenter(Integer centerId, Integer languageId) {
		
		EducationCenter center = findEducationCenter(centerId);
		
		Language language = languageService.findLanguage(languageId);

		center.removeLanguage(language);
	}

	@Transactional
	public void removeCourseFromEducationCenter(Integer centerId, Integer courseId) {
		
		EducationCenter center = findEducationCenter(centerId);
		
		Course course = courseService.findCourse(courseId);
		
		center.removeCourse(course);
	}

	@Transactional
	public void removeAccommodationFromEducationCenter(Integer centerId, Integer accommodationId) {
		
		EducationCenter center = findEducationCenter(centerId);
		
		Accommodation accommodation = accommodationService.findAccommodation(accommodationId);
		
		center.removeAccommodation(accommodation);	
	}
}
