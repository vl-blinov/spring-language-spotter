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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.blinov.language.spotter.center.EducationCenter;

@Entity
@Table(name="accommodation")
public class Accommodation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			 			 CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="education_center_id")
	private EducationCenter educationCenter;
	
	@NotBlank(message="Accommodation type is mandatory")
	@Column(name="accommodation_type")
	private String accommodationType;
	
	@NotBlank(message="Room type is mandatory")
	@Column(name="room_type")
	private String roomType;
	
	@NotBlank(message="Meal included type is mandatory")
	@Column(name="meal_included_type")
	private String mealIncludedType;
	
	@NotBlank(message="Age restriction is mandatory")
	@Column(name="age_restriction")
	private String ageRestriction;
	
	@NotNull(message="Price per week amount is mandatory")
	@Column(name="price_per_week_amount")
	private Double pricePerWeekAmount;
	
	@NotBlank(message="Price per week currency is mandatory")
	@Column(name="price_per_week_currency")
	private String pricePerWeekCurrency;
	
	public Accommodation() {
		
	}

	public Accommodation(String accommodationType, String roomType, String mealIncludedType, String ageRestriction,
						 Double pricePerWeekAmount, String pricePerWeekCurrency) {
		
		this.accommodationType = accommodationType;
		this.roomType = roomType;
		this.mealIncludedType = mealIncludedType;
		this.ageRestriction = ageRestriction;
		this.pricePerWeekAmount = pricePerWeekAmount;
		this.pricePerWeekCurrency = pricePerWeekCurrency;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getMealIncludedType() {
		return mealIncludedType;
	}

	public void setMealIncludedType(String mealIncludedType) {
		this.mealIncludedType = mealIncludedType;
	}

	public String getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(String ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	public Double getPricePerWeekAmount() {
		return pricePerWeekAmount;
	}

	public void setPricePerWeekAmount(Double pricePerWeekAmount) {
		this.pricePerWeekAmount = pricePerWeekAmount;
	}

	public String getPricePerWeekCurrency() {
		return pricePerWeekCurrency;
	}

	public void setPricePerWeekCurrency(String pricePerWeekCurrency) {
		this.pricePerWeekCurrency = pricePerWeekCurrency;
	}
}
