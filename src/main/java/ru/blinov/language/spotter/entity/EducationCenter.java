package ru.blinov.language.spotter.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="education_center")
public class EducationCenter {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="educationCenter",
			   cascade={CascadeType.DETACH, CascadeType.MERGE,
					    CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Course> courses;
	
	@OneToMany(mappedBy="educationCenter",
			   cascade={CascadeType.DETACH, CascadeType.MERGE,
					    CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Accommodation> accommodations;
	
	@Column(name="registration_fee")
	private String registrationFee;
	
	@Column(name="rating")
	private int rating;

	public EducationCenter() {
		
	}

	public EducationCenter(String name, int rating) {
		this.name = name;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Accommodation> getAccommodations() {
		return accommodations;
	}

	public void setAccommodations(List<Accommodation> accommodations) {
		this.accommodations = accommodations;
	}

	public String getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(String registrationFee) {
		this.registrationFee = registrationFee;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
