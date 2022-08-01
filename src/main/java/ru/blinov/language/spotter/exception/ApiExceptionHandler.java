package ru.blinov.language.spotter.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = RequestUrlException.class)
	public ResponseEntity<Object> handleRequestUrlException(RequestUrlException e) {
		
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiException apiException = new ApiException(e.getMessage(), badRequest, ZonedDateTime.now());
		
		return new ResponseEntity<>(apiException, badRequest);
	}
}
