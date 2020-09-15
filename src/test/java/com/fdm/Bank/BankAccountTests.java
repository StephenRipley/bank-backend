package com.fdm.Bank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdm.Bank.Models.CurrentAccount;
import com.fdm.Bank.Models.MortgageAccount;
import com.fdm.Bank.Models.SavingsAccount;
import com.fdm.Bank.Models.User;
import com.fdm.Bank.Services.CurrentAccountService;
import com.fdm.Bank.Services.MortgageAccountService;
import com.fdm.Bank.Services.SavingsAccountService;
import com.fdm.Bank.Services.UserService;

@SpringBootTest
public class BankAccountTests {
	
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	MockMvc mockMvc;

	MockHttpSession session;

	final static String CURRENT_ACCOUNT_ROOT_URI = "/CAController";

	@Autowired
	CurrentAccountService currentAccountService;

	@Autowired
	SavingsAccountService savingsAccountService;

	@Autowired
	MortgageAccountService mortgageAccountService;

	@Autowired
	UserService userService;
	
	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession()).build();
	}

	@Test
	public void testToCreateANewCurrentAccount() {

		CurrentAccount testAccount = new CurrentAccount("Debit1", new BigDecimal("1000.00"), new BigDecimal("0.50"),
				new BigDecimal("400.00"));
		currentAccountService.save(testAccount);
		Optional<CurrentAccount> accountFromDB = currentAccountService.findById(testAccount.getAccountId());
		Assertions.assertEquals(testAccount.getBalance(), accountFromDB.get().getBalance());
	}
	
	@Test
	public void testThatACurrentAccountCanBeMade_ViaController() throws Exception {

		ResultActions result = this.mockMvc.perform(post(CURRENT_ACCOUNT_ROOT_URI + "/CreateCurrentAccount")
				.session(session)
				.contentType("application/json"))
		.andExpect(status().isCreated());
		System.err.println(result.andReturn().getResponse().getContentAsString());
		
		Assertions.assertNotNull(result);
	}

	@Test
	public void testThatACurrentAccountCanBeAddedToAUser() {

		CurrentAccount testAccount = new CurrentAccount("TestAccount", new BigDecimal("100000.00"),
				new BigDecimal("4.50"), new BigDecimal("50.00"));
		currentAccountService.save(testAccount);
		Optional<User> beforeUser = userService.findById(1L);
		currentAccountService.addAccount(testAccount, beforeUser);
		Optional<User> afterUser = userService.findById(1L);
		int beforeUserList = beforeUser.get().getUserAccount().size();
		int afterUserList = afterUser.get().getUserAccount().size();
		Assertions.assertEquals(afterUserList, beforeUserList + 1);

	}

	@Test
	public void testToCreateANewSavingAccount() {

		SavingsAccount testAccount = new SavingsAccount("Savings1", new BigDecimal("100.00"), new BigDecimal("1.50"),
				"4 Withdraws");
		savingsAccountService.save(testAccount);
		Optional<SavingsAccount> accountFromDB = savingsAccountService.findById(testAccount.getAccountId());
		Assertions.assertEquals(testAccount, accountFromDB.get());

	}

	@Test
	public void testThatASavingAccountCanBeAddedToAUser() {

		SavingsAccount testAccount = new SavingsAccount("TestAccount", new BigDecimal("9000.00"),
				new BigDecimal("3.50"), "2 Withdrawls");
		savingsAccountService.save(testAccount);
		Optional<User> beforeUser = userService.findById(2L);
		savingsAccountService.addAccount(testAccount, beforeUser);
		Optional<User> afterUser = userService.findById(2L);
		int beforeUserList = beforeUser.get().getUserAccount().size();
		int afterUserList = afterUser.get().getUserAccount().size();
		Assertions.assertEquals(afterUserList, beforeUserList + 1);

	}

	@Test
	public void testToCreateANewMortgageAccount() {

		MortgageAccount testAccount = new MortgageAccount("Mortgage1", new BigDecimal("100000.00"),
				new BigDecimal("5.00"), new BigDecimal("12.00"), "12th June", new BigDecimal("140.50"));
		mortgageAccountService.save(testAccount);
		Optional<MortgageAccount> accountFromDB = mortgageAccountService.findById(testAccount.getAccountId());
		Assertions.assertEquals(testAccount, accountFromDB.get());
	}

	@Test
	public void testThatAMortgageccountCanBeAddedToAUser() {

		MortgageAccount testAccount = new MortgageAccount("MortgageTest", new BigDecimal("250000.00"),
				new BigDecimal("4.00"), new BigDecimal("24.00"), "9th June", new BigDecimal("500.50"));
		mortgageAccountService.save(testAccount);
		Optional<User> beforeUser = userService.findById(2L);
		mortgageAccountService.addAccount(testAccount, beforeUser);
		Optional<User> afterUser = userService.findById(2L);
		int beforeUserList = beforeUser.get().getUserAccount().size();
		int afterUserList = afterUser.get().getUserAccount().size();
		Assertions.assertEquals(afterUserList, beforeUserList + 1);

	}

}
