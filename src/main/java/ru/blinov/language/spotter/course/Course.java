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
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.center.EducationCenter;
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
	
	@NotBlank(message="Course type is mandatory")
	@Column(name="course_type")
	private String type;
	
	@NotBlank(message="Students per class is mandatory")
	@Column(name="students_per_class")
	private int studentsPerClass;
	
	@NotBlank(message="Course duration is mandatory")
	@Column(name="course_duration")
	private String courseDuration;
	
	@NotBlank(message="Lessons per week	 is mandatory")
	@Column(name="lessons_per_week")
	private int lessonsPerWeek;
	
	@NotBlank(message="Class time is mandatory")
	@Column(name="class_time")
	private String classTime;
	
	@NotBlank(message="Lesson duration is mandatory")
	@Column(name="lesson_duration")
	private String lessonDuration;
	
	@NotBlank(message="Age restriction is mandatory")
	@Column(name="age_restriction")
	private String ageRestriction;
	
	@NotBlank(message="Entry level is mandatory")
	@Column(name="entry_level")
	private String entryLevel;
	
	@NotBlank(message="Price per week amount is mandatory")
	@Column(name="price_per_week_amount")
	private double pricePerWeekAmount;
	
	@NotBlank(message="Price per week currency is mandatory")
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
