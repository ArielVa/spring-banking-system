package com.example.banking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.banking.exceptions.CustomerInvalidPropertiesException;

@ControllerAdvice
public class CustomersExceptionController {

	
	@ExceptionHandler(value = CustomerInvalidPropertiesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Got bad customer data")
	public ResponseEntity<String> customerInvalidProperties(Exception e) {
		System.out.println("[Customer] - Error in performing an action on a customer.");
		return ResponseEntity.badRequest().body("[Customer] - Error in performing an action on a customer.");
	}
	
}
