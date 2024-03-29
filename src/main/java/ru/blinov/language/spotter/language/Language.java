package ru.blinov.language.spotter.language;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.center.EducationCenter;
import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.course.Course;

@Entity
@Table(name="language")
public class Language {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@NotBlank(message="Name is mandatory")
	@Column(name="name")
	private String name;
	
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE,
						 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="language_country",
			   joinColumns=@JoinColumn(name="language_id"),
			   inverseJoinColumns=@JoinColumn(name="country_id"))
	private List<Country> countries;
	
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE,
				 		 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="language_city",
	  		   joinColumns=@JoinColumn(name="language_id"),
	  		   inverseJoinColumns=@JoinColumn(name="city_id"))
	private List<City> cities;
	
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="language_education_center",
			   joinColumns=@JoinColumn(name="language_id"),
			   inverseJoinColumns=@JoinColumn(name="education_center_id"))
	private List<EducationCenter> educationCenters;
	
	@OneToMany(mappedBy="language",
			   cascade=CascadeType.ALL)
	private List<Course> courses;

	public Language() {
		
	}

	public Language(String name) {
		this.name = name;
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
	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	@JsonIgnore
	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	@JsonIgnore
	public List<EducationCenter> getEducationCenters() {
		return educationCenters;
	}

	public void setEducationCenters(List<EducationCenter> educationCenters) {
		this.educationCenters = educationCenters;
	}
	
	@JsonIgnore
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
