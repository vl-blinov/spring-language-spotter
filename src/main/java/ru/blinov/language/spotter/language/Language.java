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
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.country.Country;
import ru.blinov.language.spotter.util.StringFormatter;

@Entity
@Table(name="language")
public class Language {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE,
						 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="language_country",
			   joinColumns=@JoinColumn(name="language_id"),
			   inverseJoinColumns=@JoinColumn(name="country_id"))
	private List<Country> countries;

	public Language() {
		
	}

	public Language(String name) {
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
	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	public Country getCountry(String countryName) {
		return countries.stream().filter(country -> StringFormatter.formatPathVariable(countryName).equals(country.getName())).findAny().get();
	}
}
