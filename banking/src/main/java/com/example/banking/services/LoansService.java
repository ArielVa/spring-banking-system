package com.example.banking.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking.aop.Loggable;
import com.example.banking.entities.Loan;
import com.example.banking.entities.account.Account;
import com.example.banking.exceptions.LoanInvalidPropertiesException;
import com.example.banking.repositories.AccountsRepository;
import com.example.banking.repositories.LoansRepository;

@Service
public class LoansService {
	
	@Autowired
	private LoansRepository loansRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Loggable(className = "LoansService",
			success = "A loan has been created for an account.",
			failed = "Couldn't create an loan for the account, please contant the bank for more information.",
			throwable = LoanInvalidPropertiesException.class)
	public Loan takeNewLoanOnAccount(Integer accountNum, Float amount, Float monthlyPayment, Float interestRate, Date dueDate) throws Throwable {
		Account acc = accountsRepository.getAnActiveAccountByNum(accountNum);
		Date today = new Date(System.currentTimeMillis());
		
		if(amount < 0f || monthlyPayment < 0f || interestRate < 0f || today.after(dueDate) || acc.getCustomer().isDeleted()) throw new LoanInvalidPropertiesException();

		return loansRepository.save(new Loan(amount, amount, monthlyPayment, interestRate, today, dueDate, acc));
	}
	
	@Loggable(className = "LoansService",
			success = "Loan payment had been submitted.",
			failed = "Invalid data for loan payment - please check for correct loan ID and amount.",
			throwable = LoanInvalidPropertiesException.class)
	@Transactional
	public Loan payLoan(Integer loanId, Float amount) throws LoanInvalidPropertiesException{
		Optional<Loan> oLoan = loansRepository.findById(loanId);
		
		if(oLoan.isEmpty() || amount < 0f) throw new LoanInvalidPropertiesException();
		
		Loan loan = oLoan.get();
		
		if(loan.getAccount().isSuspended() || loan.getAccount().getCustomer().isDeleted()) throw new LoanInvalidPropertiesException();
		
		float newRemainder = loan.getAmountRemained() - amount;
		loan.setAmountRemained(newRemainder >= 0f ? newRemainder : 0f);
		loan.setWasPayed(newRemainder <= 0f);
		
		return loan;
	}
	
	// TODO add method payLoanFromAccount
	
	
}


