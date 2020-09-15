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
import com.fdm.Bank.Models.MortgageDetails;
import com.fdm.Bank.Services.MortgageCalculatorService;

@RestController
@RequestMapping("/mortgageCalculator")
@CrossOrigin(origins = "http://localhost:3000")

public class MCController {

	private static Logger LOGGER = LogManager.getLogger(MCController.class);

	@Autowired
	MortgageCalculatorService mortgageCalculatorService;

	@PostMapping("/calculateMortgage")
	public ResponseEntity<MortgageDetails> getMonthlyPayment(@RequestBody MortgageDetails mortgageDetails) {

		if (mortgageCalculatorService.validateDeposit(mortgageDetails.getHousePrice(), mortgageDetails.getDeposit())) {
			LOGGER.info("Valid deposit passed in");
			if (mortgageCalculatorService.interestCheck(mortgageDetails.getInterestRate())) {
				mortgageDetails.setInterestRate(
						mortgageCalculatorService.interestCorrection(mortgageDetails.getInterestRate()));
				mortgageCalculatorService.calculateMortgageRate(mortgageDetails);
			} else {

			}
		} else {
			LOGGER.info("IN-Valid Deposit passed in: " + mortgageDetails.getDeposit());
			mortgageDetails.setMonthlyPayment(new BigDecimal("0"));
		}

		return new ResponseEntity<MortgageDetails>(mortgageDetails, HttpStatus.OK);
	}

}
