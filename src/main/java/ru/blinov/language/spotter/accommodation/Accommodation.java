package ru.blinov.language.spotter.accommodation;

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
@Table(name="accommodation")
public class Accommodation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="education_center_id")
	private EducationCenter educationCenter;
	
	@Column(name="accommodation_type")
	private String accommodationType;
	
	@Column(name="food")
	private String food;
	
	@Column(name="age_restriction")
	private String ageRestriction;
	
	@Column(name="price_per_week")
	private String pricePerWeek;

	public Accommodation() {
		
	}

	public Accommodation(EducationCenter educationCenter, String accommodationType, String food, String ageRestriction,
			String pricePerWeek) {
		this.educationCenter = educationCenter;
		this.accommodationType = accommodationType;
		this.food = food;
		this.ageRestriction = ageRestriction;
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

	public String getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(String accommodationType) {
		this.accommodationType = accommodationType;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(String ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	public String getPricePerWeek() {
		return pricePerWeek;
	}

	public void setPricePerWeek(String pricePerWeek) {
		this.pricePerWeek = pricePerWeek;
	}
}
