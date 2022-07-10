package ru.blinov.language.spotter.country;

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

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.city.City;
import ru.blinov.language.spotter.language.Language;
import ru.blinov.language.spotter.util.StringFormatter;

@Entity
@Table(name="country")
public class Country {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE,
						 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="language_country",
			   joinColumns=@JoinColumn(name="country_id"),
			   inverseJoinColumns=@JoinColumn(name="language_id"))
	private List<Language> languages;
	
	@OneToMany(mappedBy="country",
			   cascade={CascadeType.DETACH, CascadeType.MERGE,
					    CascadeType.PERSIST, CascadeType.REFRESH})
	private List<City> cities;

	public Country() {

	}

	public Country(String name) {
		this.name = name;
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
	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public City getCity(String cityName) {
		return cities.stream().filter(city -> StringFormatter.formatPathVariable(cityName).equals(city.getName())).findAny().get();
	}
}
