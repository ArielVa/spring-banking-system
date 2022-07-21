package com.example.banking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

	@Async
	@ExceptionHandler(value = CustomerInvalidPropertiesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "[Customer] - Error in performing an action on a customer.")
	public ResponseEntity<String> customerInvalidProperties(Exception e) {
		System.out.println("[CustomerController] - Error in performing an action on a customer.");
		return ResponseEntity.badRequest().build();
	}
	
	@Async
	@ExceptionHandler(value = AccountInvalidPropertiesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "[AccountsController] - Error in performing an action on an account.")
	public ResponseEntity<String> accountInvalidProperties(Exception e) {
		System.out.println("[AccountsController] - Error in performing an action on an account.");
		return ResponseEntity.badRequest().build();
	}
	
	@Async
	@ExceptionHandler(value = LoanInvalidPropertiesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "[LoansController] - Error in performing an action on a loan.")
	public ResponseEntity<String> loanInvalidProperties(Exception e) {
		System.out.println("[LoansController] - Error in performing an action on a loan.");
		return ResponseEntity.badRequest().build();
	}
	
}



