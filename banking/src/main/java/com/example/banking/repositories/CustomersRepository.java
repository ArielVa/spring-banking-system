package com.example.banking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.banking.entities.Customer;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {
	
	Customer findCustomerByCustomerIdAndIsDeletedFalse(Integer customerId);
	List<Customer> findAllByIsDeletedFalse();
	
}
