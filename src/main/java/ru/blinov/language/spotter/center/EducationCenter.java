package ru.blinov.language.spotter.center;

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
import javax.validation.constraints.NotBlank;

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
	private Integer id;
	
	@NotBlank(message="Name is mandatory")
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
	
	@NotBlank(message="Address is mandatory")
	@Column(name="address")
	private String address;
	
	@NotBlank(message="Registration fee amount is mandatory")
	@Column(name="registration_fee_amount")
	private Double registrationFeeAmount;
	
	@NotBlank(message="Registration fee currency is mandatory")
	@Column(name="registration_fee_currency")
	private String registrationFeeCurrency;
	
	@Column(name="rating")
	private Double rating;

	public EducationCenter() {
		
	}

	public EducationCenter(String name, String address, Double registrationFeeAmount, String registrationFeeCurrency, Double rating) {
		
		this.name = name;
		this.address = address;
		this.registrationFeeAmount = registrationFeeAmount;
		this.registrationFeeCurrency = registrationFeeCurrency;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Double getRegistrationFeeAmount() {
		return registrationFeeAmount;
	}

	public void setRegistrationFeeAmount(Double registrationFeeAmount) {
		this.registrationFeeAmount = registrationFeeAmount;
	}

	public String getRegistrationFeeCurrency() {
		return registrationFeeCurrency;
	}

	public void setRegistrationFeeCurrency(String registrationFeeCurrency) {
		this.registrationFeeCurrency = registrationFeeCurrency;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public void addLanguage(Language language) {
		languages.add(language);
	}
	
	public void removeLanguage(Language language) {
		languages.remove(language);
	}
	
	public void addCourse(Course course) {
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
	}
	
	public void addAccommodation(Accommodation accommodation) {
		accommodations.add(accommodation);
	}
	
	public void removeAccommodation(Accommodation accommodation) {
		accommodations.remove(accommodation);
	}
	
	public boolean hasLanguage(String languageName) {
		return languages.stream().filter(l -> l.getName().equals(languageName)).findAny().isPresent();
	}
}
