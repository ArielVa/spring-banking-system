package com.example.banking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.banking.entities.Customer;
import com.example.banking.exceptions.CustomerInvalidPropertiesException;
import com.example.banking.services.CustomersService;

@RestController
@RequestMapping("/customers")
public class CustomersController {
	
	@Autowired private CustomersService customersService;
	
	@PostMapping("/{customerId}/{name}/{email}/{age}")
	public ResponseEntity<Customer> addNewCustomer(
			@PathVariable("customerId") Integer customerId, 
			@PathVariable("name") String name,
			@PathVariable("email") String email,
			@PathVariable("age") Float age) throws Throwable {
		return ResponseEntity.ok(customersService.createNewCustomer(customerId, name, email, age));
	}
	
	@GetMapping("")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(customersService.getAllCustomers());
	}
	
	@GetMapping("get-with-active")
	public ResponseEntity<List<Customer>> getAllCustomersWithActiveAccounts() {
		return ResponseEntity.ok(customersService.getAllCustomersWithActiveAccounts());
	}
	
	@PutMapping("/{customerId}/{isDeactivated}")
	public ResponseEntity<Customer> setCustomerStatus(@PathVariable Integer customerId,
			@PathVariable("isDeactivated") Boolean isDeactivated) throws CustomerInvalidPropertiesException {
		return ResponseEntity.ok(customersService.setCustomerActiveStatus(customerId, isDeactivated));
	}
	
	@DeleteMapping("")
	public ResponseEntity<Boolean> removeAllCustomersRecords() {
		customersService.deleteAllCustomersRecords();
		return ResponseEntity.ok(true);
	}
	
	
}
