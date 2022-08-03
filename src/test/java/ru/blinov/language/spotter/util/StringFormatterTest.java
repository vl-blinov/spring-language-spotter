package ru.blinov.language.spotter.util;

import static org.assertj.core.api.Assertions.assertThat;

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
}
