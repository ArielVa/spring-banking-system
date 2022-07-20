package com.example.banking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.banking.entities.account.Account;
import com.example.banking.entities.account.AccountRiskLevel;
import com.example.banking.services.AccountsService;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
	
	@Autowired
	private AccountsService accountsService;
	
	@GetMapping("/{customerId}")
	public ResponseEntity<List<Account>> getAccounts(@PathVariable ("customerId") Integer customerId) throws Throwable {
		return ResponseEntity.ok(accountsService.getAllAccountsOfCustomer(customerId));
	}
	
	@GetMapping("/balance/{accountNum}")
	public ResponseEntity<Account> getAccount(@PathVariable("accountNum") Integer accountNum) throws Throwable {
		return ResponseEntity.ok(accountsService.getAccountByNum(accountNum));
	}
	
	@PostMapping("/add-account/{customerId}/{initialBalance}/{riskLevel}")
	public ResponseEntity<Account> addAccountToCustomer(@PathVariable("customerId") Integer customerId,
														@PathVariable("initialBalance") Float initialBalance,
														@PathVariable("riskLevel") AccountRiskLevel riskLevel) throws Throwable {
		return ResponseEntity.ok(accountsService.addNewAccountToCustomer(customerId, initialBalance, riskLevel));
	}
	
	@PutMapping("/acc-status/{accountNum}/{isSuspended}")
	public ResponseEntity<Account> suspendAccount(@PathVariable("accountNum") Integer accountNum,
												  @PathVariable("isSuspended") Boolean isSuspended) throws Throwable {
		return ResponseEntity.ok(accountsService.setAccountSuspencionStatus(accountNum, isSuspended));
	}
	
	@PutMapping("/deposit/{accountNum}/{amount}")
	public ResponseEntity<Account> depositIntoAccount(@PathVariable("accountNum") Integer accountNum,
			  @PathVariable("amount") Float amount) throws Throwable {
		return ResponseEntity.ok(accountsService.deposit(accountNum, amount));
	}
	
	@PutMapping("/withdraw/{accountNum}/{amount}")
	public ResponseEntity<Account> withdrawFromAccount(@PathVariable("accountNum") Integer accountNum,
			  @PathVariable("amount") Float amount) throws Throwable {
		return ResponseEntity.ok(accountsService.withdraw(accountNum, amount));
	}
	
	@PutMapping("/transfer/{accoutNumFrom}/{accountNumTo}/{amount}")
	public ResponseEntity<List<Account>> transferBetweenAccounts(@PathVariable("accoutNumFrom") Integer accoutNumFrom,
																@PathVariable("accountNumTo") Integer accountNumTo,
																@PathVariable("amount") Float amount) throws Throwable {
		List<Account> accounts = new ArrayList<Account>() {{
			add(accountsService.withdraw(accoutNumFrom, amount));
			add(accountsService.deposit(accountNumTo, amount));
		}};
		
		return ResponseEntity.ok(accounts);
	}
}
