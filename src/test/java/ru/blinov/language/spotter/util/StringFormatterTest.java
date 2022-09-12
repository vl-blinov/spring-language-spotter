package ru.blinov.language.spotter.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StringFormatterTest {
	
	@Test
	public void Should_replace_underscore_delimiters_with_whitespaces_and_change_the_first_letter_of_each_word_to_upper_case_in_a_given_String() {
		
		//Arrange
		String pathVariable = "erin_school_of_english";
		
		String expected = "Erin School Of English";
		
		//Act
		String result = StringFormatter.formatPathVariable(pathVariable);
		
		//Assert
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	public void Should_throw_IllegalArgumentException_when_argument_is_null() {
		
		//Arrange
		String pathVariable = null;
		
		String message = "Path variable must not be null";
		
		
		//Assert
		assertThrows(message,
				IllegalArgumentException.class,
				() -> StringFormatter.formatPathVariable(pathVariable));
	}
}
