package com.example.banking.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
	
	@GetMapping("/")
	public String getAccound(int accountNum) {
		return "hello there";
	}
}
