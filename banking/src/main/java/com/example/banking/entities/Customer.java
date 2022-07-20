package com.example.banking.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.example.banking.entities.account.Account;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="customers")
public class Customer implements Cloneable {
	
	@Id
	private int customerId;
	private String name;
	private String email;
	private float age;
	private boolean isDeleted = false;
	
	@OneToMany(mappedBy = "customer")
//	@JoinColumn(name="accountNum")
	@JsonManagedReference
	List<Account> accounts;

	public Customer() {
		super();
	}

	public Customer(int customerId, String name, String email, float age) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.age = age;
		this.email = email;
		this.accounts = new ArrayList<Account>();
		this.isDeleted = false;
	}

	public Customer(int customderId, String name, String email, float age, List<Account> accounts) {
		super();
		this.customerId = customderId;
		this.name = name;
		this.age = age;
		this.email = email;
		this.accounts = accounts;
		this.isDeleted = false;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Customer clone() {
		Customer clonned = new Customer(customerId, name, email, age, accounts);
		return clonned;
	}
	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", age=" + age
				+ ", isDeleted=" + isDeleted + "]";
	}
}
