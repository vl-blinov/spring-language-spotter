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
import ru.blinov.language.spotter.language.Language;

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
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="language_id")
	private Language language;
	
	@Column(name="course_type")
	private String type;
	
	@Column(name="students_per_class")
	private int studentsPerClass;
	
	@Column(name="course_duration")
	private String courseDuration;
	
	@Column(name="lessons_per_week")
	private int lessonsPerWeek;
	
	@Column(name="class_time")
	private String classTime;
	
	@Column(name="lesson_duration")
	private String lessonDuration;
	
	@Column(name="age_restriction")
	private String ageRestriction;
	
	@Column(name="entry_level")
	private String entryLevel;
	
	@Column(name="price_per_week_amount")
	private double pricePerWeekAmount;
	
	@Column(name="price_per_week_currency")
	private String pricePerWeekCurrency;

	public Course() {
		
	}

	public Course(String type, int studentsPerClass, String courseDuration, int lessonsPerWeek, String classTime,
			String lessonDuration, String ageRestriction, String entryLevel, double pricePerWeekAmount,
			String pricePerWeekCurrency) {
		this.type = type;
		this.studentsPerClass = studentsPerClass;
		this.courseDuration = courseDuration;
		this.lessonsPerWeek = lessonsPerWeek;
		this.classTime = classTime;
		this.lessonDuration = lessonDuration;
		this.ageRestriction = ageRestriction;
		this.entryLevel = entryLevel;
		this.pricePerWeekAmount = pricePerWeekAmount;
		this.pricePerWeekCurrency = pricePerWeekCurrency;
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
	
	public int getLessonsPerWeek() {
		return lessonsPerWeek;
	}

	public void setLessonsPerWeek(int lessonsPerWeek) {
		this.lessonsPerWeek = lessonsPerWeek;
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
	
	public String getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(String ageRestriction) {
		this.ageRestriction = ageRestriction;
	}
	
	public String getEntryLevel() {
		return entryLevel;
	}

	public void setEntryLevel(String entryLevel) {
		this.entryLevel = entryLevel;
	}

	public double getPricePerWeekAmount() {
		return pricePerWeekAmount;
	}

	public void setPricePerWeekAmount(double pricePerWeekAmount) {
		this.pricePerWeekAmount = pricePerWeekAmount;
	}

	public String getPricePerWeekCurrency() {
		return pricePerWeekCurrency;
	}

	public void setPricePerWeekCurrency(String pricePerWeekCurrency) {
		this.pricePerWeekCurrency = pricePerWeekCurrency;
	}

	@JsonIgnore
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
