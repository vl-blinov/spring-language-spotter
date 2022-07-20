package ru.blinov.language.spotter.education;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.accommodation.Accommodation;
import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.course.Course;
import ru.blinov.language.spotter.language.Language;

@Entity
@Table(name="education_center")
public class EducationCenter {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="city_id")
	private City city;
	
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE,
				 		 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="language_education_center",
	  		   joinColumns=@JoinColumn(name="education_center_id"),
	  		   inverseJoinColumns=@JoinColumn(name="language_id"))
	private List<Language> languages;
	
	@OneToMany(mappedBy="educationCenter",
			   cascade=CascadeType.ALL)
	private List<Course> courses;
	
	@OneToMany(mappedBy="educationCenter",
			   cascade=CascadeType.ALL)
	private List<Accommodation> accommodations;
	
	@Column(name="address")
	private String address;
	
	@Column(name="registration_fee_amount")
	private double registrationFeeAmount;
	
	@Column(name="registration_fee_currency")
	private String registrationFeeCurrency;
	
	@Column(name="rating")
	private double rating;

	public EducationCenter() {
		
	}

	public EducationCenter(String name, String address, double registrationFeeAmount, String registrationFeeCurrency, double rating) {
		this.name = name;
		this.address = address;
		this.registrationFeeAmount = registrationFeeAmount;
		this.registrationFeeCurrency = registrationFeeCurrency;
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
	
	@JsonIgnore
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	@JsonIgnore
	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	@JsonIgnore
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	@JsonIgnore
	public List<Accommodation> getAccommodations() {
		return accommodations;
	}

	public void setAccommodations(List<Accommodation> accommodations) {
		this.accommodations = accommodations;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getRegistrationFeeAmount() {
		return registrationFeeAmount;
	}

	public void setRegistrationFeeAmount(double registrationFeeAmount) {
		this.registrationFeeAmount = registrationFeeAmount;
	}

	public String getRegistrationFeeCurrency() {
		return registrationFeeCurrency;
	}

	public void setRegistrationFeeCurrency(String registrationFeeCurrency) {
		this.registrationFeeCurrency = registrationFeeCurrency;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
}
