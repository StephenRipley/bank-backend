package com.fdm.Bank.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.Bank.Models.CurrentAccount;
import com.fdm.Bank.Models.CurrentTransaction;
import com.fdm.Bank.Services.CurrentAccountService;
import com.fdm.Bank.Services.UserService;

@RestController
@RequestMapping("/CAController")
@CrossOrigin(origins = "http://localhost:3000")
public class CurrentAccountController {
	
	private static Logger LOGGER = LogManager.getLogger(CurrentAccountService.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	CurrentAccountService currentAccountService;
	
	@PostMapping("/CreateCurrentAccount")
	public ResponseEntity<CurrentAccount> createCurrentAccount() {
		CurrentAccount newCurrentAccount = currentAccountService.createCurrentAccount();
		return new ResponseEntity<CurrentAccount>(newCurrentAccount, HttpStatus.CREATED);
	}
	
	@PatchMapping("/Deposit")
	public ResponseEntity<CurrentAccount> deposit(@RequestBody CurrentTransaction currentTransaction) {
		CurrentAccount updatedAccount = currentAccountService.deposit(currentTransaction);
		return new ResponseEntity<CurrentAccount>(updatedAccount, HttpStatus.OK);
	}
	
	@PatchMapping("/Withdrawl")
	public ResponseEntity<CurrentAccount> withdrawl(@RequestBody CurrentTransaction currentTransaction) {
		CurrentAccount updatedAccount = currentAccountService.withdrawl(currentTransaction);
		return new ResponseEntity<CurrentAccount>(updatedAccount, HttpStatus.OK);
	}

}
