package com.example.banking.services;

import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.example.banking.aop.Loggable;
import com.example.banking.configs.AccountConfigs;
import com.example.banking.entities.Customer;
import com.example.banking.entities.account.Account;
import com.example.banking.entities.account.AccountRiskLevel;
import com.example.banking.exceptions.AccountInvalidPropertiesException;
import com.example.banking.exceptions.CustomerInvalidPropertiesException;
import com.example.banking.repositories.AccountsRepository;
import com.example.banking.repositories.CustomersRepository;


@Service
public class CustomersService {

	@Autowired
	private CustomersRepository customersRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private ApplicationContext context;
	
	@Value("${account.age.min}")
	private float minAge;
	
	@Value("${account.name.min-length}") 
	private float minLengthName;
	
	
	@Loggable(className = "CustomersService",
			success = "Added a new customer successfully.",
			failed = "Couldn't create new customer with the given data.",
			throwable = CustomerInvalidPropertiesException.class)
	public Customer createNewCustomer(Integer customerId, String name, String email, Float age) throws CustomerInvalidPropertiesException {
	
		if(!customersRepository.findById(customerId).isEmpty()) throw new CustomerInvalidPropertiesException();
		if(name.trim().length() <= minLengthName) throw new CustomerInvalidPropertiesException();
		if(age < minAge) throw new CustomerInvalidPropertiesException();
		
		return customersRepository.save(new Customer(customerId, name, email, age));
	}
	
	
	public Customer getCustomerById(Integer customerId) throws CustomerInvalidPropertiesException {
		Optional<Customer> customer  = customersRepository.findById(customerId);
		if(customer.isEmpty()) throw new CustomerInvalidPropertiesException();
		return customer.get();	
	}
	
	public List<Customer> getAllCustomers() {
		return customersRepository.findAll();
	}


	public void deleteAllCustomersRecords() {
		customersRepository.deleteAll();;
	}

	@Loggable(className = "CustomersService", 
			success = "Customer with all of its accounts where removed.",
			failed = "Couldn't remove a customer, please check the provided details again.",
			throwable = CustomerInvalidPropertiesException.class)
	@Transactional
	public Customer removeCustomer(Integer customerId) throws CustomerInvalidPropertiesException {
		Customer customer = getCustomerById(customerId);
		customer.setDeleted(true);
		return customer;
	}
}
