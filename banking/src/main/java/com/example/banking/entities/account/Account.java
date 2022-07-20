package com.example.banking.entities.account;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.example.banking.entities.Customer;
import com.example.banking.entities.Loan;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="accounts")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int accountNum;
	
	private float balance;
	
	@Enumerated(EnumType.ORDINAL)
	private AccountRiskLevel accountRiskLevel;
	
	private boolean isSuspended;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	@JsonBackReference
	private Customer customer;
	
	@OneToMany(mappedBy = "account")
	@JsonManagedReference
	private List<Loan> loans;
	
	public Account() {
		super();
	}
	
	public Account(int accountNum, float balance, AccountRiskLevel accountRiskLevel, boolean isSuspended,
			Customer customer, List<Loan> loans) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.accountRiskLevel = accountRiskLevel;
		this.isSuspended = isSuspended;
		this.customer = customer;
		this.loans = loans;
	}

	public Account(int accountNum, float balance, AccountRiskLevel accountRiskLevel, boolean isSuspended, Customer customer) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.accountRiskLevel = accountRiskLevel;
		this.isSuspended = isSuspended;
		this.customer = customer;
		this.loans = new ArrayList<Loan>();
	}
	
	public Account(float balance, AccountRiskLevel accountRiskLevel, Customer customer) {
		super();
		this.balance = balance;
		this.accountRiskLevel = accountRiskLevel;
		this.customer = customer;
		this.isSuspended = false;
		this.loans = new ArrayList<Loan>();
	}

	public int getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public AccountRiskLevel getAccountRiskLevel() {
		return accountRiskLevel;
	}

	public void setAccountRiskLevel(AccountRiskLevel accountRiskLevel) {
		this.accountRiskLevel = accountRiskLevel;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	@Override
	public String toString() {
		return "Account [accountNum=" + accountNum + ", balance=" + balance + ", accountRiskLevel=" + accountRiskLevel
				+ ", isSuspended=" + isSuspended + ", customer=" + customer + ", loans=" + loans + "]";
	}

	
}
