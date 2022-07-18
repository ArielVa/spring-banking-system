package com.example.banking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking.repositories.AccountsRepository;

@Service
public class AccountsService {
	
	@Autowired
	private AccountsRepository accountsRepository;
}
