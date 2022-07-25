package com.example.banking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.banking.entities.Loan;

@Repository
public interface LoansRepository extends JpaRepository<Loan, Integer> {
	
	@Query(value = "select * "
			+ "from loans "
			+ "left join accounts on accounts.account_num = loans.account_num "
			+ "inner join customers on accounts.customer_id = customers.customer_id "
			+ "where loans.was_payed = false and customers.is_deleted = false",
			nativeQuery = true)
	List<Loan> getAllUnpaidLoansOfActiveCustomers();
	
	List<Loan> findAllByWasPaidFalse();
}
