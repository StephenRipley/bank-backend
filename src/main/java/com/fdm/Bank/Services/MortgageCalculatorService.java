package com.fdm.Bank.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.fdm.Bank.Models.LoanDetails;
import com.fdm.Bank.Models.MortgageDetails;

@Service
public class MortgageCalculatorService {

	private static Logger LOGGER = LogManager.getLogger(MortgageCalculatorService.class);

	public boolean validateDeposit(BigDecimal housePrice, BigDecimal deposit) {
		BigDecimal depositMin = housePrice.multiply(new BigDecimal("0.15"));
		LOGGER.info(depositMin);
		if (deposit.compareTo(housePrice) == 1 || deposit.compareTo(housePrice) == 0) {
			LOGGER.info("Deposit is equal to or greater than house price");
			return false;
		} else if (depositMin.compareTo(deposit) == -1 || depositMin.compareTo(deposit) == 0) {
			return true;
		} else {
			LOGGER.info("Deposit is less than 15% of houseprice");
			return false;
		}
	}

	public Boolean interestCheck(BigDecimal interestRate) {
		if (interestRate.compareTo(new BigDecimal("0.99")) == 1 && interestRate.compareTo(new BigDecimal("10")) == -1) {
			LOGGER.info("Interest rate is between 1 and 10");
			return true;
		}
		LOGGER.info("Interest is below 1 or above 10");
		return false;
	}

	public BigDecimal interestCorrection(BigDecimal interestRate) {
		interestRate = interestRate.divide(new BigDecimal("100"), 6, RoundingMode.UP);
		LOGGER.info("Interest corrected to: " + interestRate);
		return interestRate;
	}

	public MortgageDetails calculateMortgageRate(MortgageDetails mortgageDetails) {
		BigDecimal twelve = new BigDecimal(12.00);
		BigDecimal loanAmount = mortgageDetails.getHousePrice().subtract(mortgageDetails.getDeposit());
		BigDecimal convertedInterestRate = mortgageDetails.getInterestRate().divide(twelve, 6, RoundingMode.UP);
		BigDecimal numberOfMonths = mortgageDetails.getTermInYears().multiply(twelve);
		BigDecimal interestToPower = (convertedInterestRate.add(new BigDecimal(1.00))).pow(numberOfMonths.intValue());
		BigDecimal numerator = interestToPower.multiply(convertedInterestRate);
		BigDecimal denominator = interestToPower.subtract(new BigDecimal(1.00));
		BigDecimal result = loanAmount.multiply(numerator.divide(denominator, 6, RoundingMode.UP));
		BigDecimal roundedResult = (result.setScale(2, RoundingMode.HALF_EVEN));
		mortgageDetails.setMonthlyPayment(roundedResult);
		LOGGER.info("Mortgage Calculated to: " + result);
		LOGGER.info("Returning: " + mortgageDetails);
		return mortgageDetails;
	}

	public Boolean mortgageValidCheck(LoanDetails loanDetails) {
		BigDecimal yearlyWage = loanDetails.getYearlyWage();
		BigDecimal loan = loanDetails.getLoan();
		BigDecimal threeYearWages = yearlyWage.multiply(new BigDecimal("3"));
		if (threeYearWages.compareTo(loan) == 1 || threeYearWages.compareTo(loan) == 0) {
			loanDetails.setLoanAffordable("a");
			LOGGER.info("Mortgage Valid!");
			return true;
		}
		loanDetails.setLoanAffordable("b");
		LOGGER.info("Mortgage IN-Valid!");
		return false;
	}

	public static void checkElement(String name, Element elem) {
		if (elem == null) {
			throw new RuntimeException("Unable to find " + name);
		}
	}

}
