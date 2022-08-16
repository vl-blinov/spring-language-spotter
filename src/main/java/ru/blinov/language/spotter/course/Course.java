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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.language.Language;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
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
	private String courseType;
	
	@NotNull(message="Students per class is mandatory")
	@Column(name="students_per_class")
	private Integer studentsPerClass;
	
	@NotBlank(message="Course duration is mandatory")
	@Column(name="course_duration")
	private String courseDuration;
	
	@NotNull(message="Lessons per week	 is mandatory")
	@Column(name="lessons_per_week")
	private Integer lessonsPerWeek;
	
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
	
	@NotNull(message="Price per week amount is mandatory")
	@Column(name="price_per_week_amount")
	private Double pricePerWeekAmount;
	
	@NotBlank(message="Price per week currency is mandatory")
	@Column(name="price_per_week_currency")
	private String pricePerWeekCurrency;

	public Course() {
		
	}

	public Course(String courseType, Integer studentsPerClass, String courseDuration, Integer lessonsPerWeek, String classTime,
				  String lessonDuration, String ageRestriction, String entryLevel, Double pricePerWeekAmount,
				  String pricePerWeekCurrency) {
		
		this.courseType = courseType;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonIgnore
	public EducationCenter getEducationCenter() {
		return educationCenter;
	}

	public void setEducationCenter(EducationCenter educationCenter) {
		this.educationCenter = educationCenter;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public Integer getStudentsPerClass() {
		return studentsPerClass;
	}

	public void setStudentsPerClass(Integer studentsPerClass) {
		this.studentsPerClass = studentsPerClass;
	}

	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}
	
	public Integer getLessonsPerWeek() {
		return lessonsPerWeek;
	}

	public void setLessonsPerWeek(Integer lessonsPerWeek) {
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

	public Double getPricePerWeekAmount() {
		return pricePerWeekAmount;
	}

	public void setPricePerWeekAmount(Double pricePerWeekAmount) {
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
