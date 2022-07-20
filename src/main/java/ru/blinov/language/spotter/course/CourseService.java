package ru.blinov.language.spotter.course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {
	
	private CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findAllCourses(String centerName, String languageName) {	
		
		List<Course> courses = courseRepository.findAllByCenterName(centerName);
		
		if(courses.isEmpty()) {
			throw new RuntimeException("Education center with name '" + centerName + "' does not exist");
		}
		
		if(courses.contains(null)) {
			courses.clear();
		}
		
		if(!courses.isEmpty() && !courses.contains(null)) {
			courses = courses.stream().filter(course -> course.getLanguage().getName().equals(languageName)).collect(Collectors.toList());
		}

		return courses;
	}
	
	@Transactional
	public void saveCourse(Course course) {
		
		if(course.getId() == 0) {
			
			String centerName = course.getEducationCenter().getName();
			
			Optional<Course> courseOfCenter = courseRepository.findOneByCenterName(centerName);
			
			if(courseOfCenter.isEmpty()) {
				throw new RuntimeException("Education center with name '" + centerName + "' does not exist");
			}
		}
		
		courseRepository.save(course);	
	}
	
	@Transactional
	public void deleteCourse(int courseId) {
		
		Optional<Course> course = courseRepository.findById(courseId);
		
		if(course.isEmpty()) {
			throw new RuntimeException("Course with id " + courseId + "is not found");
		}
		
		courseRepository.deleteById(courseId);
	}	
}
