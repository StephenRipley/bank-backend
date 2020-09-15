package com.fdm.Bank.Controllers;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.Bank.Models.LoanDetails;
import com.fdm.Bank.Services.LoanCheckerService;
import com.fdm.Bank.Services.MortgageCalculatorService;

@RestController
@RequestMapping("/loanChecker")
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {

	private static Logger LOGGER = LogManager.getLogger(LoanCheckerService.class);
	
	@Autowired 
	LoanCheckerService loanCheckerService;
	
	@Autowired 
	MortgageCalculatorService mortgageCalculatorService;
	
	@PostMapping("/checkLoan")
	public ResponseEntity<LoanDetails> getMaximumAllowedLoan(@RequestBody LoanDetails loanDetails) {
		loanDetails.setCalculatedLoanAmount(loanCheckerService.calculateMaximumLoan(loanDetails));
		return new ResponseEntity<LoanDetails>(loanDetails, HttpStatus.OK);
	}
	
	@PostMapping("/calculateNetWage")
	public ResponseEntity<BigDecimal> scrapeNetWage(@RequestBody LoanDetails loanDetails) {
		BigDecimal calculatedNetWage = loanCheckerService.scrapeNetWage(loanDetails);
		return new ResponseEntity<BigDecimal>(calculatedNetWage, HttpStatus.OK);
	}

	@PostMapping("/calculateLoan")
	public ResponseEntity<LoanDetails> calculateLoan(@RequestBody LoanDetails loanDetails) {
		if (!loanDetails.getNetWage()){
			LOGGER.info("Before deductions: " + loanDetails.getYearlyWage());
			loanDetails.setYearlyWage(loanCheckerService.scrapeNetWage(loanDetails));
			LOGGER.info("After deductions: " + loanDetails.getYearlyWage());
			loanDetails.setCalculatedLoanAmount(loanCheckerService.calculateMaximumLoan(loanDetails));
			return new ResponseEntity<>(loanDetails, HttpStatus.OK);
		}
		loanDetails.setCalculatedLoanAmount(loanCheckerService.calculateMaximumLoan(loanDetails));
		return new ResponseEntity<>(loanDetails, HttpStatus.OK);
	}
	

}
