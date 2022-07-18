package com.example.banking.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking.annotations.Loggable;
import com.example.banking.entities.Customer;
import com.example.banking.entities.account.AccountLimitType;
import com.example.banking.exceptions.CustomerInvalidPropertiesException;
import com.example.banking.repositories.CustomersRepository;

@Service
public class CustomersService {

	@Autowired
	private CustomersRepository customersRepository;
	
	@Loggable(className = CustomersService.class,
			successMessage = "Added a new customer successfully.",
			failedMessage = "Couldn't create new customer with the given data.")
	public Customer createNewCustomer(Integer customerId, String name, String email, Float age) throws CustomerInvalidPropertiesException {
		if(!customersRepository.findById(customerId).isEmpty()) throw new CustomerInvalidPropertiesException();
		if(name.trim().length() <= 2) throw new CustomerInvalidPropertiesException();
		if(age < 18) throw new CustomerInvalidPropertiesException();
		return customersRepository.save(new Customer(customerId, name, email, age));
	}
	
}
