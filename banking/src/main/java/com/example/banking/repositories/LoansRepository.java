package com.example.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.banking.entities.Loan;

@Repository
public interface LoansRepository extends JpaRepository<Loan, Integer> {

}
