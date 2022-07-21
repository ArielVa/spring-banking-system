package com.example.banking.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.banking.aop.Loggable;
import com.example.banking.entities.Customer;
import com.example.banking.exceptions.CustomerInvalidPropertiesException;
import com.example.banking.repositories.CustomersRepository;


@Service
public class CustomersService {

	@Autowired private CustomersRepository customersRepository;
	
	@Value("${customer.age.min}") 
	private float minAge;
	
	@Value("${customer.name.min-length}") 
	private float minLengthName;
	
	@Value("${customer.email.min-length}")
	private float minLengthEmail;
	
	@Loggable(className = "CustomersService",
			success = "Added a new customer successfully.",
			failed = "Couldn't create new customer with the given data.",
			throwable = CustomerInvalidPropertiesException.class)
	public Customer createNewCustomer(Integer customerId, String name, String email, Float age) throws CustomerInvalidPropertiesException {

		if(!customersRepository.findById(customerId).isEmpty()) throw new CustomerInvalidPropertiesException();
		if(name.trim().length() <= minLengthName) throw new CustomerInvalidPropertiesException();
		if(email.trim().length() <= minLengthEmail) throw new CustomerInvalidPropertiesException();
		if(age < minAge) throw new CustomerInvalidPropertiesException();
		return customersRepository.save(new Customer(customerId, name, email, age));
	}
	
	private Customer findCustomerById(Integer customerId) throws CustomerInvalidPropertiesException {
		Optional<Customer> oCustomer  = customersRepository.findById(customerId);
		if(oCustomer.isEmpty()) throw new CustomerInvalidPropertiesException();
		return oCustomer.get();	
	}
	
	public Customer findActiveCustomerById(Integer customerId) throws CustomerInvalidPropertiesException {
		return customersRepository.findCustomerByCustomerIdAndIsDeletedFalse(customerId);
	}
	
	public List<Customer> getAllCustomers() {
		return customersRepository.findAll();
	}
	
	public List<Customer> getAllCustomersWithActiveAccounts() {	
		return customersRepository.findAllByIsDeletedFalse();
	}

	@Loggable(className = "CustomersService", 
			success = "Customer status was altered.",
			failed = "Couldn't remove a customer, please check the provided details again.",
			throwable = CustomerInvalidPropertiesException.class)
	@Transactional
	public Customer setCustomerActiveStatus(Integer customerId, Boolean isDeactivated) throws CustomerInvalidPropertiesException {
		Customer customer = findCustomerById(customerId);
		customer.setDeleted(isDeactivated);
		return customer;
	}
	
	public void deleteAllCustomersRecords() {
		customersRepository.deleteAll();
	}
}
