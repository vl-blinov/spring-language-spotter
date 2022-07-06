package ru.blinov.language.spotter.course;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.education.EducationCenter;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="education_center_id")
	private EducationCenter educationCenter;
	
	@Column(name="course_type")
	private String type;
	
	@Column(name="students_per_class")
	private int studentsPerClass;
	
	@Column(name="course_duration")
	private String courseDuration;
	
	@Column(name="class_time")
	private String classTime;
	
	@Column(name="lesson_duration")
	private String lessonDuration;
	
	@Column(name="price_per_week")
	private String pricePerWeek;

	public Course() {
		
	}

	public Course(EducationCenter educationCenter, String type, int studentsPerClass, String courseDuration,
			String classTime, String lessonDuration, String pricePerWeek) {
		this.educationCenter = educationCenter;
		this.type = type;
		this.studentsPerClass = studentsPerClass;
		this.courseDuration = courseDuration;
		this.classTime = classTime;
		this.lessonDuration = lessonDuration;
		this.pricePerWeek = pricePerWeek;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@JsonIgnore
	public EducationCenter getEducationCenter() {
		return educationCenter;
	}

	public void setEducationCenter(EducationCenter educationCenter) {
		this.educationCenter = educationCenter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStudentsPerClass() {
		return studentsPerClass;
	}

	public void setStudentsPerClass(int studentsPerClass) {
		this.studentsPerClass = studentsPerClass;
	}

	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}

	public String getClassTime() {
		return classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	public String getLessonDuration() {
		return lessonDuration;
	}

	public void setLessonDuration(String lessonDuration) {
		this.lessonDuration = lessonDuration;
	}

	public String getPricePerWeek() {
		return pricePerWeek;
	}

	public void setPricePerWeek(String pricePerWeek) {
		this.pricePerWeek = pricePerWeek;
	}
}
