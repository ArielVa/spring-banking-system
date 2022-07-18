package com.example.banking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking.entities.Customer;
import com.example.banking.entities.account.AccountLimitType;
import com.example.banking.exceptions.CustomerInvalidPropertiesException;
import com.example.banking.services.CustomersService;

@RestController
@RequestMapping("/customers")
public class CustomersController {
	
	@Autowired
	private CustomersService customersService;
	
	@PostMapping("/")
	public ResponseEntity<Customer> addNewCustomer(Integer customerId, String name, String email, Float age) throws CustomerInvalidPropertiesException {
		return ResponseEntity.ok(customersService.createNewCustomer(customerId, name, email, age));
	}
	
}
