package com.example.banking.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking.entities.Customer;
import com.example.banking.entities.account.Account;
import com.example.banking.entities.account.AccountRiskLevel;
import com.example.banking.exceptions.AccountInvalidPropertiesException;
import com.example.banking.services.AccountsService;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
	
	@Autowired
	private AccountsService accountsService;
	
	@GetMapping("/")
	public String getAccound(int accountNum) {
		return "hello there";
	}
	
	@PutMapping("/add-account/{customerId}/{initialBalance}")
	public ResponseEntity<Account> addAccountToCustomer(@PathVariable("customerId") Integer customerId,
														@PathVariable("initialBalance") Float initialBalance) throws Throwable {
		return ResponseEntity.ok(accountsService.addNewAccountToCustomer(customerId, initialBalance));
	}
	
	@PutMapping("/suspent/{accountNum}/{isSuspended}")
	public ResponseEntity<Account> suspendAccount(@PathVariable("accountNum") Integer accountNum,
												  @PathVariable("isSuspended") Boolean isSuspended) throws Throwable {
		return ResponseEntity.ok(accountsService.setAccountSuspencionStatus(accountNum, isSuspended));
	}
}
