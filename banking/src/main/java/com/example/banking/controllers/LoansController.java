package com.example.banking.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.banking.entities.Loan;
import com.example.banking.exceptions.LoanInvalidPropertiesException;
import com.example.banking.services.LoansService;

@RestController
@RequestMapping("/loans")
public class LoansController {

	@Autowired private LoansService loansService;
	
	@PostMapping("/{accountNum}/{amount}/{monthlyPayment}/{interestRate}/{dueDate}")
	public ResponseEntity<Loan> takeNewLoanOnAccount(@PathVariable("accountNum") Integer accountNum,
			@PathVariable("amount") Float amount, 
			@PathVariable("monthlyPayment") Float monthlyPayment, 
			@PathVariable("interestRate") Float interestRate, 
			@PathVariable("dueDate") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) Date dueDate) throws Throwable {
		return ResponseEntity.ok(loansService.takeNewLoanOnAccount(accountNum, amount, monthlyPayment, interestRate, dueDate));
	}
	
	@PutMapping("/pay/{loanId}/{amount}")
	public ResponseEntity<Loan> payLoan(@PathVariable("loanId") Integer loanId,
										@PathVariable("amount") Float amount) throws LoanInvalidPropertiesException {
		return ResponseEntity.ok(loansService.payLoan(loanId, amount));
	}
	
	@GetMapping("/{accountNum}")
	public ResponseEntity<List<Loan>> getLoansOfAccount(@PathVariable("accountNum") Integer accountNum) {
		return ResponseEntity.ok(loansService.getLoansOfAccount(accountNum));
	}
}
