package ru.blinov.language.spotter.entity;

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

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
