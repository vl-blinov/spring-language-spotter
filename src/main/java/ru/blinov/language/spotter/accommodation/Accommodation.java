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
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.center.EducationCenter;

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
	
	@NotBlank(message="Accommodation type is mandatory")
	@Column(name="accommodation_type")
	private String accommodationType;
	
	@NotBlank(message="Room is mandatory")
	@Column(name="room")
	private String room;
	
	@NotBlank(message="Food is mandatory")
	@Column(name="food")
	private String food;
	
	@NotBlank(message="Age restriction is mandatory")
	@Column(name="age_restriction")
	private String ageRestriction;
	
	@NotBlank(message="Price per week amount is mandatory")
	@Column(name="price_per_week_amount")
	private double pricePerWeekAmount;
	
	@NotBlank(message="Price per week currency is mandatory")
	@Column(name="price_per_week_currency")
	private String pricePerWeekCurrency;
	
	public Accommodation() {
		
	}

	public Accommodation(String accommodationType, String room, String food, String ageRestriction,
						 double pricePerWeekAmount, String pricePerWeekCurrency) {
		
		this.accommodationType = accommodationType;
		this.room = room;
		this.food = food;
		this.ageRestriction = ageRestriction;
		this.pricePerWeekAmount = pricePerWeekAmount;
		this.pricePerWeekCurrency = pricePerWeekCurrency;
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

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
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

	public double getPricePerWeekAmount() {
		return pricePerWeekAmount;
	}

	public void setPricePerWeekAmount(double pricePerWeekAmount) {
		this.pricePerWeekAmount = pricePerWeekAmount;
	}

	public String getPricePerWeekCurrency() {
		return pricePerWeekCurrency;
	}

	public void setPricePerWeekCurrency(String pricePerWeekCurrency) {
		this.pricePerWeekCurrency = pricePerWeekCurrency;
	}
}
