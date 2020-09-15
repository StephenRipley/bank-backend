package com.fdm.Bank;

import java.math.BigDecimal;

import com.fdm.Bank.Models.ContractType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdm.Bank.Models.MortgageDetails;
import com.fdm.Bank.Models.LoanDetails;
import com.fdm.Bank.Models.StudentLoan;
import com.fdm.Bank.Services.LoanCheckerService;
import com.fdm.Bank.Services.MortgageCalculatorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MortgageCalculatorTests {

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	MockMvc mockMvc;

	MockHttpSession session;

	final static String MORTGAGE_CALCULATOR_ROOT_URI = "/mortgageCalculator";

	@Autowired
	MortgageCalculatorService mortgageCalculatorService;

	@Autowired
	LoanCheckerService loanCheckerService;

	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession()).build();
	}

	@Test
	void TestThatMinimumDepositIsGreaterThan15PercentOfHousePrice() {
		BigDecimal housePrice = new BigDecimal("655000.00");
		BigDecimal deposit = new BigDecimal("575858.00");
		Boolean valid = mortgageCalculatorService.validateDeposit(housePrice, deposit);
		Assertions.assertTrue(valid);
	}

	@Test
	void TestThatMinimumDepositIsEqualTo15PercentOfHousePrice() {
		BigDecimal housePrice = new BigDecimal("655000.00");
		BigDecimal deposit = new BigDecimal("98250.00");
		Boolean valid = mortgageCalculatorService.validateDeposit(housePrice, deposit);
		Assertions.assertTrue(valid);
	}

	@Test
	void TestThatMinimumDepositIsLessThan15PercentOfHousePrice() {
		BigDecimal housePrice = new BigDecimal("655000.00");
		BigDecimal deposit = new BigDecimal("8250.00");
		Boolean valid = mortgageCalculatorService.validateDeposit(housePrice, deposit);
		Assertions.assertFalse(valid);
	}

	@Test
	void TestThatDepositIsNotGreaterThanHousePrice() {
		BigDecimal housePrice = new BigDecimal("655000.00");
		BigDecimal deposit = new BigDecimal("665000.00");
		Boolean valid = mortgageCalculatorService.validateDeposit(housePrice, deposit);
		Assertions.assertFalse(valid);
	}

	@Test
	void testMonthlyPaymentCanBeCalculated() {
		MortgageDetails mortgageDetails = new MortgageDetails(new BigDecimal("500000.00"), new BigDecimal("75000.00"),
				new BigDecimal("30"), new BigDecimal("0.04"), null);
		MortgageDetails returnedMortgage = mortgageCalculatorService.calculateMortgageRate(mortgageDetails);
		BigDecimal monthlyPrice = returnedMortgage.getMonthlyPayment();

		Assertions.assertNotNull(monthlyPrice);
	}

	@Test
	public void calculateMortgageRate() throws Exception {
		MortgageDetails mortgageDetails = new MortgageDetails(new BigDecimal("500000.00"), new BigDecimal("75000.00"),
				new BigDecimal("30"), new BigDecimal("0.04"), null);
		this.mockMvc
				.perform(post(MORTGAGE_CALCULATOR_ROOT_URI + "/calculateMortgage").session(session)
						.contentType("application/json").content(objectMapper.writeValueAsString(mortgageDetails)))
				.andExpect(status().isOk());
	}

	@Test
	void testInterestCheckReturnsTrue() {
		BigDecimal interest = new BigDecimal("5");
		Assertions.assertTrue(mortgageCalculatorService.interestCheck(interest));
	}

	@Test
	void testInterestCantBeUnderOne() {
		BigDecimal interest = new BigDecimal("0.5");
		Assertions.assertFalse(mortgageCalculatorService.interestCheck(interest));
	}

	@Test
	void testInterestCantBeOverTen() {
		BigDecimal interest = new BigDecimal("20");
		Assertions.assertFalse(mortgageCalculatorService.interestCheck(interest));
	}

	@Test
	void testInterestCorrection() {
		BigDecimal interest = new BigDecimal("10");
		BigDecimal newInterest = mortgageCalculatorService.interestCorrection(interest);
		Assertions.assertNotEquals(interest, newInterest);
	}

	@Test
	void testCheckYouCanGetMortgage() {
		BigDecimal yearlyWage = new BigDecimal("25000");
		BigDecimal loan = new BigDecimal("70000");
		BigDecimal netWage = new BigDecimal("0.00");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "", (new BigDecimal("2500")),
				false, StudentLoan.NONE);
		Assertions.assertTrue(mortgageCalculatorService.mortgageValidCheck(loanDetails));
	}

	@Test
	void testCheckYouCantGetMortgage() {
		BigDecimal yearlyWage = new BigDecimal("15000");
		BigDecimal loan = new BigDecimal("70000");
		BigDecimal netWage = new BigDecimal("0.00");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "", (new BigDecimal("2500")),
				false, StudentLoan.NONE);
		Assertions.assertFalse(mortgageCalculatorService.mortgageValidCheck(loanDetails));
	}
}