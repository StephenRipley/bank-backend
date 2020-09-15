package com.fdm.Bank;

import java.math.BigDecimal;

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
import com.fdm.Bank.Models.ContractType;
import com.fdm.Bank.Models.LoanDetails;
import com.fdm.Bank.Models.StudentLoan;
import com.fdm.Bank.Services.LoanCheckerService;
import com.fdm.Bank.Services.MortgageCalculatorService;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanCheckerTests {
	
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	MockMvc mockMvc;

	MockHttpSession session;
	
	final static String LOAN_CHECKER_ROOT_URI = "/loanChecker";

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
	void testCheckYouCanGetMortgage() {
		BigDecimal yearlyWage = new BigDecimal("25000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		Assertions.assertTrue(loanCheckerService.mortgageValidCheck(loanDetails));
	}

	@Test
	void testCheckYouCantGetMortgage() {
		BigDecimal yearlyWage = new BigDecimal("15000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		Assertions.assertFalse(loanCheckerService.mortgageValidCheck(loanDetails));
	}

	@Test
	void testThatWhenYouCantGetMortgageYouGetProposedAnAlternative() {
		BigDecimal yearlyWage = new BigDecimal("15000");
		BigDecimal loan = new BigDecimal("70000");
		BigDecimal maximumAllowedLoan = new BigDecimal("45000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal calculatedAllowedLoan = loanCheckerService.calculateMaximumLoan(loanDetails);
		Assertions.assertTrue(maximumAllowedLoan.equals(calculatedAllowedLoan));
	}

	@Test
	void ifYourWageIs6000YouPayNoTaxOrPayNIC() {
		BigDecimal yearlyWage = new BigDecimal("6000");
		BigDecimal loan = new BigDecimal("7000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal newNetWage = loanCheckerService.calculateNetWage(loanDetails);
		Assertions.assertEquals(newNetWage, yearlyWage);
	}

	@Test
	void ifYourWageIsLessThan12500YouPayNoTaxButPayNIC() {
		BigDecimal yearlyWage = new BigDecimal("10000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal newNetWage = loanCheckerService.calculateNetWage(loanDetails);
		Assertions.assertNotEquals(newNetWage, yearlyWage);
	}

	@Test
	void ifYourWageIs13000YouPayTax() {
		BigDecimal yearlyWage = new BigDecimal("13000");
		BigDecimal loan = new BigDecimal("70000");;
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal newNetWage = loanCheckerService.calculateNetWage(loanDetails);
		BigDecimal roughNewNetWage = new BigDecimal(12950);
		Assertions.assertTrue(newNetWage.compareTo(roughNewNetWage) == -1);
	}

	@Test
	void ifYourWageIs16000YouPayMoreTax() {
		BigDecimal yearlyWage = new BigDecimal("16000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal newNetWage = loanCheckerService.calculateNetWage(loanDetails);
		BigDecimal roughNewNetWage = new BigDecimal(15500);
		Assertions.assertTrue(newNetWage.compareTo(roughNewNetWage) == -1);

	}

	@Test
	void ifYourWageIs28000YouPayMoreTax() {
		BigDecimal yearlyWage = new BigDecimal("28000");
		BigDecimal loan = new BigDecimal("70000");;
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal newNetWage = loanCheckerService.calculateNetWage(loanDetails);
		BigDecimal roughNewNetWage = new BigDecimal(26500);
		Assertions.assertTrue(newNetWage.compareTo(roughNewNetWage) == -1);
	}

	@Test
	void ifYourWageIs60000YouPayMoreTax() {
		BigDecimal yearlyWage = new BigDecimal("60000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal newNetWage = loanCheckerService.calculateNetWage(loanDetails);
		BigDecimal roughNewNetWage = new BigDecimal(59000);
		Assertions.assertTrue(newNetWage.compareTo(roughNewNetWage) == -1);
	}

	@Test
	void ifYourWageIs160000YouPayMoreTax() {
		BigDecimal yearlyWage = new BigDecimal("160000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal newNetWage = loanCheckerService.calculateNetWage(loanDetails);
		BigDecimal roughNewNetWage = new BigDecimal(110000);
		Assertions.assertTrue(newNetWage.compareTo(roughNewNetWage) == -1);
	}

	
	@Test
	void ifYourWageIsTripleYouPayMoreTax() {
		BigDecimal yearlyWage = new BigDecimal("20000");
		BigDecimal loan = new BigDecimal("60000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("20000")),false, StudentLoan.NONE);
		Assertions.assertTrue(loanCheckerService.mortgageValidCheck(loanDetails));
	}

	@Test
	void ifYourWageIsOver100000PersonalTaxWillLower() {
		BigDecimal yearlyWage = new BigDecimal("110000");
		BigDecimal taxFreePersonalAllowance = new BigDecimal("12500");
		BigDecimal newTaxFreePersonalAllowance = loanCheckerService
				.calculateTaxFreePersonalAllowance(taxFreePersonalAllowance, yearlyWage);
		Assertions.assertNotEquals(taxFreePersonalAllowance, newTaxFreePersonalAllowance);
	}

	@Test
	void ifYourWageIsUnder100000PersonalTaxWillStaySame() {
		BigDecimal yearlyWage = new BigDecimal("90000");
		BigDecimal taxFreePersonalAllowance = new BigDecimal("12500");
		BigDecimal newTaxFreePersonalAllowance = loanCheckerService
				.calculateTaxFreePersonalAllowance(taxFreePersonalAllowance, yearlyWage);
		Assertions.assertEquals(taxFreePersonalAllowance, newTaxFreePersonalAllowance);
	}

	@Test
	void ifYourWageIs11000PersonalTaxWillNotBeZero() {
		BigDecimal yearlyWage = new BigDecimal("110000");
		BigDecimal taxFreePersonalAllowance = new BigDecimal("12500");
		BigDecimal newTaxFreePersonalAllowance = loanCheckerService
				.calculateTaxFreePersonalAllowance(taxFreePersonalAllowance, yearlyWage);
		Assertions.assertNotEquals(new BigDecimal(0), newTaxFreePersonalAllowance);
	}

	@Test
	void ifYourWageIsUnder125000PersonalTaxWillBeZero() {
		BigDecimal yearlyWage = new BigDecimal("125000");
		BigDecimal taxFreePersonalAllowance = new BigDecimal("12500");
		BigDecimal newTaxFreePersonalAllowance = loanCheckerService
				.calculateTaxFreePersonalAllowance(taxFreePersonalAllowance, yearlyWage);
		Assertions.assertEquals(new BigDecimal(0), newTaxFreePersonalAllowance);
	}
	
	@Test
	void testThatNetWageIsScrapedAfterUsingExternalCalculator() {
		BigDecimal yearlyWage = new BigDecimal("30000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.NONE);
		BigDecimal serviceCalculatedNetWage = loanCheckerService.scrapeNetWage(loanDetails);
		Assertions.assertNotNull(serviceCalculatedNetWage);
		
	}
	
	@Test
	void testThatScrapedNetWageWithType1LoanForYearlyWage30000Is23064point43() {
		BigDecimal yearlyWage = new BigDecimal("30000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.PLAN1);
		BigDecimal serviceCalculatedNetWage = loanCheckerService.scrapeNetWage(loanDetails);
		BigDecimal expectedNetWage = new BigDecimal("23064.43");
		Assertions.assertEquals(expectedNetWage, serviceCalculatedNetWage);
		
	}
	
	@Test
	void testThatScrapedNetWageWithType2LoanForYearlyWage30000Is23712point43() {
		BigDecimal yearlyWage = new BigDecimal("30000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.PLAN2);
		BigDecimal serviceCalculatedNetWage = loanCheckerService.scrapeNetWage(loanDetails);
		BigDecimal expectedNetWage = new BigDecimal("23712.43");
		Assertions.assertEquals(expectedNetWage, serviceCalculatedNetWage);
		
	}
	
	@Test
	void testThatScrapedNetWageWithPostgraduateLoanForYearlyWage30000Is23472point43() {
		BigDecimal yearlyWage = new BigDecimal("30000");
		BigDecimal loan = new BigDecimal("70000");
		LoanDetails loanDetails = new LoanDetails(yearlyWage, loan, ContractType.FULLTIME, "",
				(new BigDecimal("2500")),false, StudentLoan.POSTGRADUATE);
		BigDecimal serviceCalculatedNetWage = loanCheckerService.scrapeNetWage(loanDetails);
		BigDecimal expectedNetWage = new BigDecimal("23472.43");
		Assertions.assertEquals(expectedNetWage, serviceCalculatedNetWage);
		
	}
	

}
