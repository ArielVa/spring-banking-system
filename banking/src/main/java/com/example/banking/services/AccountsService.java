package com.example.banking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.banking.aop.Loggable;
import com.example.banking.entities.Customer;
import com.example.banking.entities.account.Account;
import com.example.banking.entities.account.AccountRiskLevel;
import com.example.banking.exceptions.AccountInvalidPropertiesException;
import com.example.banking.repositories.AccountsRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;

@Service
public class AccountsService {
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private CustomersService customersService;
	
	public Account getAccountByNum(Integer accountNum) throws Throwable {
		Optional<Account> account = accountsRepository.findById(accountNum);
		if(account.isEmpty()) throw new AccountInvalidPropertiesException();
		return account.get();
	}
	
	@Loggable(className = "AccountsService",
			success = "A new account was added to the customer.",
			failed = "Failed to create a new account for the customer.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account addNewAccountToCustomer(Integer customerId, Float initialBalance) throws Throwable {
		Customer customer = customersService.getCustomerById(customerId);
		List<Account> cAccounts = customer.getAccounts();
		
		if(initialBalance < 0f) throw new AccountInvalidPropertiesException();
		
		Account acc = new Account(initialBalance, AccountRiskLevel.LOW, customer);
		accountsRepository.save(acc);
		
		cAccounts.add(acc);
		customer.setAccounts(cAccounts);
		
		return acc;
	}
	
	@Loggable(className = "AccountsService",
			success = "An account has been suspended.",
			failed = "Failure - could not find matching account number.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account setAccountSuspencionStatus(Integer accountNum, Boolean isSuspended) throws Throwable {
		
		// TODO implement deleteById, throw exception if can't find account (?)
		
		Account account = getAccountByNum(accountNum);
		account.setSuspended(isSuspended);
		return account;
	}
}
