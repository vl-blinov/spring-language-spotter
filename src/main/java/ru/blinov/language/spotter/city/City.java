package ru.blinov.language.spotter.city;

import java.util.List;
import java.util.Optional;

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

import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.education.EducationCenter;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.util.StringFormatter;

@Entity
@Table(name="city")
public class City {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE,
		 		 		 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="language_city",
		   	   joinColumns=@JoinColumn(name="city_id"),
		   	   inverseJoinColumns=@JoinColumn(name="language_id"))
	private List<Language> languages;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
						 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="country_id")
	private Country country;
	
	@OneToMany(mappedBy="city",
			   cascade= {CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	private List<EducationCenter> educationCenters;

	public City() {
		
	}

	public City(String name, Country country) {
		this.name = name;
		this.country = country;
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
	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	@JsonIgnore
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	@JsonIgnore
	public List<EducationCenter> getEducationCenters() {
		return educationCenters;
	}

	public void setEducationCenters(List<EducationCenter> educationCenters) {
		this.educationCenters = educationCenters;
	}
	
	public boolean hasLanguage(String languageName) {
		return languages.stream().filter(language -> StringFormatter.formatPathVariable(languageName).equals(language.getName())).findAny().isPresent();
	}
	
	public EducationCenter getEducationCenter(String centerName) {
		return educationCenters.stream().filter(center -> StringFormatter.formatPathVariable(centerName).equals(center.getName())).findAny().get();
	}
}
