package ru.blinov.language.spotter.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="city")
public class City {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
						 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="city_id")
	private Country country;
	
	@OneToMany(mappedBy="city",
			   cascade={CascadeType.DETACH, CascadeType.MERGE,
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<EducationCenter> getEducationCenters() {
		return educationCenters;
	}

	public void setEducationCenters(List<EducationCenter> educationCenters) {
		this.educationCenters = educationCenters;
	}
}
