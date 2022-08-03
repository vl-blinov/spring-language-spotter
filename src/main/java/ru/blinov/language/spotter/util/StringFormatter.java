package ru.blinov.language.spotter.util;

import org.springframework.util.StringUtils;

public class StringFormatter {
	
	public static String formatPathVariable(String pathVariable) {

		if(!pathVariable.contains("_")) {
			return StringUtils.capitalize(pathVariable);
		}
		
		String[] array = StringUtils.delimitedListToStringArray(pathVariable, "_");
		
		array = capitalizeArrayElements(array);

		return StringUtils.arrayToDelimitedString(array, " ");
	}
	
	private static String[] capitalizeArrayElements(String[] array) {
		
		for(int i = 0; i < array.length; i++) {
			array[i] = StringUtils.capitalize(array[i]);
		}
		
		return array;
	}
}
