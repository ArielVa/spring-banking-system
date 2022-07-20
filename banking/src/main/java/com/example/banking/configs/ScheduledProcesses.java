package com.example.banking.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.banking.services.LoansService;

@Component
public class ScheduledProcesses {
	
	@Autowired
	private LoansService loansService;
	
	@Scheduled(initialDelay = 18000, fixedDelay = 18000)
	public void payAllLoansFromAccounts()
	{
		loansService.payLoansFromAccounts();
	}
}
