package ru.blinov.language.spotter.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="course_id")
	private EducationCenter educationCenter;
	
	@Column(name="type")
	private String type;
	
	@Column(name="students_per_class")
	private int studentsPerClass;
	
	@Column(name="course_duration")
	private String courseDuration;
	
	@Column(name="class_time")
	private String classTime;
	
	@Column(name="class_duration")
	private String classDuration;
	
	@Column(name="price")
	private String price;

	public Course() {
		
	}

	public Course(EducationCenter educationCenter, String type, int studentsPerClass, String courseDuration,
			String classTime, String classDuration, String price) {
		this.educationCenter = educationCenter;
		this.type = type;
		this.studentsPerClass = studentsPerClass;
		this.courseDuration = courseDuration;
		this.classTime = classTime;
		this.classDuration = classDuration;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getClassDuration() {
		return classDuration;
	}

	public void setClassDuration(String classDuration) {
		this.classDuration = classDuration;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
