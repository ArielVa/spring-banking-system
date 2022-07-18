package com.example.banking.entities.account;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.banking.entities.Customer;

@Entity
@Table(name="accounts")
public class Account {
	
	@Id
	private int accountNum;
	
	private float balance;
	
	@Enumerated(EnumType.ORDINAL)
	private AccountLimitType accountLimitType;
	
	private boolean isSuspended;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "customerId")
	private Customer customer;

	public Account() {
		super();
	}

	public Account(int accountNum, float balance, AccountLimitType accountLimitType, boolean isSuspended) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.accountLimitType = accountLimitType;
		this.isSuspended = isSuspended;
	}

	public Account(int accountNum, float balance, AccountLimitType accountLimitType, boolean isSuspended, Customer customer) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.accountLimitType = accountLimitType;
		this.isSuspended = isSuspended;
		this.customer = customer;
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

	public AccountLimitType getAccountLimitType() {
		return accountLimitType;
	}

	public void setAccountLimitType(AccountLimitType accountLimitType) {
		this.accountLimitType = accountLimitType;
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

	@Override
	public String toString() {
		return "Account [accountNum=" + accountNum + ", balance=" + balance + ", accountLimitType=" + accountLimitType
				+ ", isSuspended=" + isSuspended + "]";
	}
}
