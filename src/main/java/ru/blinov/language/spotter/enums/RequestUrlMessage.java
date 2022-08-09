package ru.blinov.language.spotter.enums;

public enum RequestUrlMessage {
	
	LANGUAGE_NOT_FOUND("Language is not found."),
	
	COUNTRY_NOT_FOUND("Country is not found."),
	
	CITY_NOT_FOUND("City is not found."),
	
	EDUCATION_CENTER_NOT_FOUND("Education center is not found."),
	
	COURSE_NOT_FOUND("Course is not found."),
	
	ACCOMMODATION_NOT_FOUND("Accommodation is not found."),

	COUNTRY_LANGUAGE_DISCR("Discrepancy between country and language. Country is not found for language."),
	
	CITY_LANGUAGE_DISCR("Discrepancy between city and language. City is not found for language."),
	
	EDUCATION_CENTER_LANGUAGE_DISCR("Discrepancy between education center and language. Education center is not found for language."),
	
	CITY_COUNTRY_DISCR("Discrepancy between city and country. City is not found for country."),
	
	EDUCATION_CENTER_CITY_DISCR("Discrepancy between education center and city. Education center is not found for city.");
	
	private final String message;

	private RequestUrlMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
