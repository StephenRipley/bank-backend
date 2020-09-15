package com.fdm.Bank.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fdm.Bank.Models.LoanDetails;

@Service
public class LoanCheckerService {
	
	private static Logger LOGGER = LogManager.getLogger(LoanCheckerService.class);
	
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
	
	public BigDecimal calculateMaximumLoan(LoanDetails loanDetails) {
		LOGGER.info("LoanDetails object from react: " + loanDetails);
		BigDecimal yearlyWage = loanDetails.getYearlyWage();
		BigDecimal loan = loanDetails.getLoan();
		if (mortgageValidCheck(loanDetails)) {
		} else {
			loan = yearlyWage.multiply(new BigDecimal("3"));
		}
		LOGGER.info("Calculate maximum loan method Returning Loan: " + loan);
		return loan;
	}

	public BigDecimal calculateTaxFreePersonalAllowance(BigDecimal allowance, BigDecimal yearlyIncome) {
		Double yearlyIncomeAfterCalculation = yearlyIncome.subtract(new BigDecimal(100000)).doubleValue();
		if (yearlyIncomeAfterCalculation < 2) {
			LOGGER.info("Yearly income is too low to change tax free allowance");
			return allowance;
		} else if (yearlyIncomeAfterCalculation >= (allowance.intValue() * 2)) {
			LOGGER.info("Yearly income is so high, tax free allowance is zero");
			return new BigDecimal(0);
		} else {
			int count = 0;
			LOGGER.info("Calculating how much of tax allowance is to be removed");
			while (yearlyIncomeAfterCalculation >= 1.99) {
				yearlyIncomeAfterCalculation -= 2;
				++count;
			}
			LOGGER.info("Returning amount of allowance remaining for yearly income.");
			return new BigDecimal(allowance.doubleValue() - count);
		}
	}

	public BigDecimal taxBracketCalculation(BigDecimal grossYearlyWage) {
		BigDecimal starterPersonalAllowance = calculateTaxFreePersonalAllowance(new BigDecimal(12500),grossYearlyWage);
		BigDecimal starterTaxable = grossYearlyWage.subtract(starterPersonalAllowance);
		if(starterTaxable.doubleValue() <= 0){
			return new BigDecimal(0);
		}else {
			if (starterTaxable.doubleValue() <= 2085) {
				return starterTaxable.multiply(new BigDecimal(0.19));
			} else {
				BigDecimal firstTaxAmount = new BigDecimal(2085).multiply(new BigDecimal(0.19)).setScale(2, RoundingMode.UP);
				BigDecimal nextTaxAmount = starterTaxable.subtract(new BigDecimal(2085));
				return firstTaxAmount.add(basicTaxBracketCalculation(nextTaxAmount));
			}
		}
	}

	private BigDecimal basicTaxBracketCalculation(BigDecimal taxableAmount) {
		if(taxableAmount.doubleValue() <= 10573){
			return taxableAmount.multiply(new BigDecimal(0.20));
		}else{
			BigDecimal nextTaxAmount = taxableAmount.subtract(new BigDecimal(10573));
			BigDecimal secondTaxableAmount = new BigDecimal(10573).multiply(new BigDecimal(0.20)).setScale(2, RoundingMode.UP);
			return secondTaxableAmount.add(intermediateTaxBracketCalculation(nextTaxAmount));
		}
	}

	private BigDecimal intermediateTaxBracketCalculation(BigDecimal taxableAmount) {
		if (taxableAmount.doubleValue() <= 18272){
			return taxableAmount.multiply(new BigDecimal(0.21));
		}else{
			BigDecimal nextTaxAmount = taxableAmount.subtract(new BigDecimal(18272));
			BigDecimal thirdTaxableAmount = new BigDecimal(18272).multiply(new BigDecimal(0.21)).setScale(2, RoundingMode.UP);
			return thirdTaxableAmount.add(higherTaxBracketCalculation(nextTaxAmount));
		}
	}

	private BigDecimal higherTaxBracketCalculation(BigDecimal taxableAmount) {
		if (taxableAmount.doubleValue() <= 106570){
			return taxableAmount.multiply(new BigDecimal(0.41));
		}else{
			BigDecimal nextTaxAmount = taxableAmount.subtract(new BigDecimal(106570));
			BigDecimal forthTaxableAmount = new BigDecimal(106570).multiply(new BigDecimal(0.41)).setScale(2, RoundingMode.UP);
			return forthTaxableAmount.add(topTaxBracketCalculation(nextTaxAmount));
		}
	}

	private BigDecimal topTaxBracketCalculation(BigDecimal taxableAmount) {
		return taxableAmount.multiply(new BigDecimal(0.46)).setScale(2, RoundingMode.UP);
	}

	public BigDecimal calculateNetWage (LoanDetails loanDetails){
		BigDecimal yearlyWage = loanDetails.getYearlyWage();
		BigDecimal tax = taxBracketCalculation(yearlyWage);
		LOGGER.info("Tax Deduction: " + tax);
		BigDecimal NIC = nationalInsuranceContributions(yearlyWage);
		return yearlyWage.subtract(tax).subtract(NIC);
	}

	public BigDecimal nationalInsuranceContributions(BigDecimal yearlyWage){
		BigDecimal weeklyWage = yearlyWage.divide(new BigDecimal(52.00),2,RoundingMode.UP);
		BigDecimal weeklyWageDeductible = weeklyWage.subtract(new BigDecimal(182));
		if(weeklyWageDeductible.doubleValue() >= 0) {
			if (weeklyWageDeductible.doubleValue() <= 780) {
				return weeklyWageDeductible.multiply(new BigDecimal(0.12)).multiply(new BigDecimal(52));
			} else {
				BigDecimal amountAfterFirstContributions = weeklyWage.subtract(new BigDecimal(780));
				BigDecimal twelvePercentDeduction = weeklyWageDeductible.multiply(new BigDecimal(0.12));
				BigDecimal twoPercentDeduction = amountAfterFirstContributions.multiply(new BigDecimal(0.02));
				BigDecimal weeklyTotal = twelvePercentDeduction.add(twoPercentDeduction);
				return weeklyTotal.multiply(new BigDecimal(52)).setScale(2, RoundingMode.UP);
			}
		}else{
			return new BigDecimal(0);
		}
	}

	public BigDecimal scrapeNetWage(LoanDetails loanDetails) {

		try {

			String url = "https://www.thesalarycalculator.co.uk/salary.php/salary.php";

			String yearlyWage = loanDetails.getYearlyWage().toString();

			Connection.Response resp =
					Jsoup.connect(url)
							.userAgent("Mozilla/5.0")
							.timeout(30000)
							.method(Connection.Method.GET)
							.execute();

			Document respDoc = resp.parse();
			Element netWageForm = respDoc.select("form").first();
			checkElement("form element", netWageForm);
			FormElement form = (FormElement) netWageForm;

			Element netWage = form.select("input.hero__input").first();
			checkElement("netwage input", netWage);
			netWage.val(yearlyWage);

			Element scottishCheckbox = form.select("input#check1").first();
			checkElement("boolean checkbox scottish", scottishCheckbox);
			scottishCheckbox.attr("checked", "checked");
			
			if (loanDetails.getStudentLoan().getName().equals("Repayment Plan 1")) {
				Element planOneCheckbox = form.selectFirst("input[name=plan1]");
				checkElement("checkbox plan1", planOneCheckbox);
				planOneCheckbox.attr("checked", "checked");
				
			 } else if (loanDetails.getStudentLoan().getName().equals("Repayment Plan 2")) {
				 Element planTwoCheckbox = form.selectFirst("input[name=plan2]");
				 checkElement("checkbox plan2", planTwoCheckbox);
				 planTwoCheckbox.attr("checked", "checked");
				 
			 } else if (loanDetails.getStudentLoan().getName().equals("Postgraduate Loan")) {
				 Element postgraduateCheckbox = form.selectFirst("input[name=postgraduate]");
				 checkElement("checkbox postgraduate", postgraduateCheckbox);
				 postgraduateCheckbox.attr("checked", "checked");
			 }

			Document results = form.submit().cookies(resp.cookies()).post();
			Element scrapedTakeHomeWage =  results.getElementsByClass("results__cell results__cell--overlined takehome").first();

			String htmlTagValue = scrapedTakeHomeWage.text();
			String stringDigits = htmlTagValue.replaceAll("[^0-9.]", "");

			BigDecimal scrapedNetWage = new BigDecimal(stringDigits);

			return scrapedNetWage;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void checkElement(String name, Element elem) {
		if (elem == null) {
			throw new RuntimeException("Unable to find " + name);
		}
	}
}
