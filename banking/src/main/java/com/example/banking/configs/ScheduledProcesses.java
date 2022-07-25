package com.example.banking.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.banking.entities.CurrencyExchangeRates;
import com.example.banking.services.AccountsService;
import com.example.banking.services.EmailService;
import com.example.banking.services.LoansService;
import org.springframework.core.env.Environment;

@Component
public class ScheduledProcesses {
	
	@Autowired private AccountsService accountsService;
	@Autowired private LoansService loansService;
	@Autowired private EmailService emailService;
	@Autowired private Environment environment;
	@Autowired private RestTemplate restTemplate;

	private static final String URL = "https://api.exchangerate-api.com/v4/latest/";
	
	@Value("${currency.base.coin}")
	private String baseCurrency;
	
//	@Scheduled(initialDelay = 18000, fixedDelay = 18000)
	@Scheduled(cron = "@daily")	
	public void payAllLoansFromAccounts()
	{
		loansService.payLoansFromAccounts();
	}
	
//	@Scheduled(initialDelay = 2000, fixedDelay = 36000)
	@Scheduled(cron = "@daily")
	public void emailReportSender() throws MessagingException {
		String emailAddress = environment.getProperty("spring.mail.destination");
		
		List<String> messages = new ArrayList<String>();
		accountsService.findAllNonSuspendedAccountsBeyondRiskLevel()
		.forEach(acc -> {
			messages.add(acc.getCustomer().getName() + " has an account number of " + acc.getAccountNum() + " beyond its risk level.\n");
		});
		
		if(messages.size() <= 0) return;
		String body = "";
		
		for(String msg: messages) {
			body+=msg;
		}
		
		System.out.println("Sent accounts status email");
		emailService.sendEmail(emailAddress, new String[] {emailAddress}, "Customer Reports", body);
	}
	
	@Bean(name = "getCurrencyExchangeRates")
	@Scheduled(cron = "@daily")
	public Map<String, Float> getCurrencyRatesFromAPI () {
		System.out.println(restTemplate.getForObject(URL + baseCurrency, CurrencyExchangeRates.class)
				.getRates());
		return restTemplate.getForObject(URL + baseCurrency, CurrencyExchangeRates.class)
				.getRates();
	}
}
