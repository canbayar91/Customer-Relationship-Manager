package com.spring.crm.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spring.crm.error.ErrorResponse;
import com.spring.crm.error.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(NotFoundException exception) {
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
		ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
		return responseEntity;
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
		ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
}
