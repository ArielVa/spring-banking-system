package com.example.banking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.example.banking.aop.Loggable;
import com.example.banking.entities.Customer;
import com.example.banking.entities.account.Account;
import com.example.banking.entities.account.AccountRiskLevel;
import com.example.banking.exceptions.AccountInvalidPropertiesException;
import com.example.banking.exceptions.CustomerInvalidPropertiesException;
import com.example.banking.repositories.AccountsRepository;
import com.example.banking.repositories.CustomersRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Service
public class AccountsService {
	
	@Autowired private AccountsRepository accountsRepository;
	@Autowired private CustomersRepository customersRepository;
	@Autowired private ApplicationContext context; 
	
	private Customer findExistingCustomerById(int customerId) throws CustomerInvalidPropertiesException {
		Customer customer = customersRepository.findCustomerByCustomerIdAndIsDeletedFalse(customerId);
		if(customer == null) throw new CustomerInvalidPropertiesException();
		return customer;
	}
	
	private float convertAmountByCurrency(float amount, String currency) {
		HashMap<String, Float> currencyExchangeRates = context.getBean("getCurrencyExchangeRates", HashMap.class);
		return amount * (1.0f / currencyExchangeRates.get(currency));
	}
	
	public List<Account> findAllNonSuspendedAccounts() {
		return accountsRepository.findAllByIsSuspendedFalse();
	}
	
	public List<Account> findAllNonSuspendedAccountsBeyondRiskLevel() {
		HashMap<AccountRiskLevel, Float> accountLimitByRisk = context.getBean("accountLimitByType", HashMap.class);
		return findAllNonSuspendedAccounts()
				.stream()
				.filter(acc -> acc.getBalance() <= accountLimitByRisk.get(acc.getAccountRiskLevel()))
				.collect(Collectors.toList());
	}
	
	@Loggable(className = "AccountsService",
			success = "Fetched the account",
			failed = "Can't find an account with the given number.",
			throwable = AccountInvalidPropertiesException.class)
	public Account getAccountByNum(Integer accountNum) throws AccountInvalidPropertiesException { 
		Account acc = accountsRepository.getAnActiveAccountByNum(accountNum);
		if(acc == null) throw new AccountInvalidPropertiesException();
		return acc;
	}
	
	@Loggable(className = "AccountsService",
			success = "Fetched the accounts of the customer",
			failed = "Can't find a customer with the given id.",
			throwable = AccountInvalidPropertiesException.class)
	public List<Account> getAllAccountsOfCustomer(Integer customerId) throws Throwable {
		return findExistingCustomerById(customerId).getAccounts()
				.stream()
				.filter(acc -> !acc.isSuspended())
				.collect(Collectors.toList());
	}
	
	@Loggable(className = "AccountsService",
			success = "A new account was added to the customer.",
			failed = "Failed to create a new account for the customer.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account addNewAccountToCustomer(Integer customerId, Float initialBalance, AccountRiskLevel riskLevel) throws Throwable {
		Customer customer = findExistingCustomerById(customerId);
		List<Account> cAccounts = customer.getAccounts();
		
		if(initialBalance < 0f) throw new AccountInvalidPropertiesException();
		
		Account acc = new Account(initialBalance, riskLevel, customer);
		accountsRepository.save(acc);
		
		cAccounts.add(acc);
		customer.setAccounts(cAccounts);
		
		return acc;
	}
	
	@Loggable(className = "AccountsService",
			success = "An account status has been changed.",
			failed = "Failure - could not find matching account number.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account setAccountSuspencionStatus(Integer accountNum, Boolean isSuspended) throws Throwable {
		Optional<Account> oAcc = accountsRepository.findById(accountNum);
		if(oAcc.isEmpty()) throw new AccountInvalidPropertiesException();
		Account acc = oAcc.get();
		acc.setSuspended(isSuspended);
		return acc;
	}
	
	@Loggable(className = "AccountsService",
			success = "A deposit into an account has been made.",
			failed = "Failed to deposit into the account, please check the account and amount data.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account deposit(Integer accountNum, Float amount) throws Throwable {
		Account acc = accountsRepository.getAnActiveAccountByNum(accountNum);
		if(amount < 0f) throw new AccountInvalidPropertiesException();
		acc.setBalance(acc.getBalance() + amount);
		return acc;
	}
	
	@Loggable(className = "AccountsService",
			success = "A deposit into an account using foreign currency has been made.",
			failed = "Failed to deposit into the account using foreign currency, please check the account and amount data.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account deposit(Integer accountNum, Float amount, String currency) throws Throwable {
		return deposit(accountNum, convertAmountByCurrency(amount, currency));
	}
	
	@Loggable(className = "AccountsService",
			success = "A withdrawal from an account has been made.",
			failed = "Can't withdraw from the account, please check the account input and make contanct with your bank if the issue persists.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account withdraw(Integer accountNum, Float amount) throws Throwable {
		Account acc = accountsRepository.getAnActiveAccountByNum(accountNum);
		if(amount < 0f) throw new AccountInvalidPropertiesException();
		
		HashMap<AccountRiskLevel, Float> accountLimitByRisk = context.getBean("accountLimitByType", HashMap.class);
		float newBalance = acc.getBalance() - amount;
		if(accountLimitByRisk.get(acc.getAccountRiskLevel()) > newBalance) throw new AccountInvalidPropertiesException();

		acc.setBalance(newBalance);
		return acc;
	}
	
	@Loggable(className = "AccountsService",
			success = "A withdrawal from an account using foreign currency has been made.",
			failed = "Can't withdraw from the account, please check the account input and make contanct with your bank if the issue persists.",
			throwable = AccountInvalidPropertiesException.class)
	@Transactional
	public Account withdraw(Integer accountNum, Float amount, String currency) throws Throwable {
		return withdraw(accountNum, convertAmountByCurrency(amount, currency));
	}

	
}
