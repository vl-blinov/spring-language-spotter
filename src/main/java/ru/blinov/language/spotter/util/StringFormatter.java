package ru.blinov.language.spotter.util;

import org.springframework.util.StringUtils;

public class StringFormatter {
	
	public static String formatPathVariable(String string) {

		if(!string.contains("_")) {
			return StringUtils.capitalize(string);
		}
		
		String[] array = StringUtils.delimitedListToStringArray(string, "_");
		
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
