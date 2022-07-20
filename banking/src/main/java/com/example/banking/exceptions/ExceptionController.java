package com.example.banking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

	
	@ExceptionHandler(value = CustomerInvalidPropertiesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Got bad customer data")
	public ResponseEntity<String> customerInvalidProperties(Exception e) {
		System.out.println("[CustomerController] - Error in performing an action on a customer.");
		return ResponseEntity.badRequest().body("[Customer] - Error in performing an action on a customer.");
	}
	
	@ExceptionHandler(value = AccountInvalidPropertiesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Got bad account data")
	public ResponseEntity<String> accountInvalidProperties(Exception e) {
		System.out.println("[AccountsController] - Error in performing an action on an account.");
		return ResponseEntity.badRequest().body("[Account] - Error in performing an action on an account.");
	}
	
	@ExceptionHandler(value = LoanInvalidPropertiesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Got bad account data")
	public ResponseEntity<String> loanInvalidProperties(Exception e) {
		System.out.println("[LoansController] - Error in performing an action on a loan.");
		return ResponseEntity.badRequest().body("[Loan] - Error in performing an action on a loan.");
	}
	
}
