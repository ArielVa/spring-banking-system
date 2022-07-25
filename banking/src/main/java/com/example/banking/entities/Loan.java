package com.example.banking.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.banking.entities.account.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "loans")
public class Loan  {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int loanId;
	private float amountGiven;
	private float amountRemained;
	private float monthlyPayment;
	private boolean wasPaid = false;
	private float interestRate;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date takenDate;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dueDate;
	
	@ManyToOne
	@JoinColumn(name = "accountNum")
	@JsonBackReference
	private Account account;
	
	public Loan() {
		super();
	}

	public Loan(int loanId, float amountGiven, float amountRemained, float monthlyPayment, boolean wasPaid,
			float interestRate, Date takenDate, Date dueDate, Account account) {
		super();
		this.loanId = loanId;
		this.amountGiven = amountGiven;
		this.amountRemained = amountRemained;
		this.monthlyPayment = monthlyPayment;
		this.wasPaid = wasPaid;
		this.interestRate = interestRate;
		this.takenDate = takenDate;
		this.dueDate = dueDate;
		this.account = account;
	}
	
	public Loan(float amountGiven, float amountRemained, float monthlyPayment, float interestRate, Date takenDate,
			Date dueDate, Account account) {
		super();
		this.amountGiven = amountGiven;
		this.amountRemained = amountRemained;
		this.monthlyPayment = monthlyPayment;
		this.interestRate = interestRate;
		this.takenDate = takenDate;
		this.dueDate = dueDate;
		this.account = account;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public float getAmountGiven() {
		return amountGiven;
	}

	public void setAmountGiven(float amountGiven) {
		this.amountGiven = amountGiven;
	}

	public float getAmountRemained() {
		return amountRemained;
	}

	public void setAmountRemained(float amountRemained) {
		this.amountRemained = amountRemained;
	}

	public float getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(float monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public boolean isWasPaid() {
		return wasPaid;
	}

	public void setWasPaid(boolean wasPaid) {
		this.wasPaid = wasPaid;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public Date getTakenDate() {
		return takenDate;
	}

	public void setTakenDate(Date takenDate) {
		this.takenDate = takenDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Loan clone() {
 
		return new Loan(loanId, amountGiven, amountRemained, monthlyPayment, wasPaid,
			interestRate, takenDate, dueDate, account);
	}
	
	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", amountGiven=" + amountGiven + ", amountRemained=" + amountRemained
				+ ", monthlyPayment=" + monthlyPayment + ", wasPaid=" + wasPaid + ", interestRate=" + interestRate
				+ ", takenDate=" + takenDate + ", dueDate=" + dueDate + "]";
	}	
	
	
}
