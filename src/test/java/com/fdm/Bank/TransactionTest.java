package com.fdm.Bank;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
import com.fdm.Bank.Models.CurrentTransaction;
import com.fdm.Bank.Models.User;
import com.fdm.Bank.Services.CurrentAccountService;
import com.fdm.Bank.Services.UserService;

@SpringBootTest
public class TransactionTest {
	
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	MockMvc mockMvc;

	MockHttpSession session;

	final static String CURRENT_ACCOUNT_ROOT_URI = "/CAController";
	
	@Autowired
	UserService userService;
	
	@Autowired
	CurrentAccountService currentAccountService;
	
	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession()).build();
	}
	
	@Test
	public void testThatACurrentTransactionCanBeCreated() {
		Optional<User> user = userService.findById(1L);
		Assertions.assertNotNull(user);
	}
	
	@Test
	public void testThatAnAccountbalanceCanModified() {
		CurrentAccount currentAccount = currentAccountService.findById(1).get();
		BigDecimal balanceBeforeAdding = currentAccount.getBalance();

		CurrentTransaction currentTransaction = new CurrentTransaction(LocalDate.of(2020, 06, 29), "monthly wage", "Incoming", "Approved", new BigDecimal("2000.00"), currentAccount);
		
		CurrentAccount updatedAccount = currentAccountService.deposit(currentTransaction);
		BigDecimal balanceAfterAdding = updatedAccount.getBalance();
		assertNotEquals(balanceBeforeAdding, balanceAfterAdding);
	}
	
	@Test
	public void testThatADepositCanBeMade() throws Exception {
		CurrentAccount usersCurrentAccount = currentAccountService.findById(1).get();
		CurrentTransaction currentTransaction = new CurrentTransaction(LocalDate.of(2020, 06, 29), "monthly wage", "Incoming", "Approved", new BigDecimal("2000.00"), usersCurrentAccount);
		
		ResultActions result = this.mockMvc.perform(patch(CURRENT_ACCOUNT_ROOT_URI + "/Deposit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(currentTransaction)))
		.andExpect(status().isOk());
		
		Assertions.assertNotEquals(usersCurrentAccount, result.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	public void testThatAWithdrawlCanBeMade() throws Exception {
		CurrentAccount usersCurrentAccount = currentAccountService.findById(1).get();
		CurrentTransaction currentTransaction = new CurrentTransaction(LocalDate.of(2020, 06, 29), "student loan", "Outgoing", "Approved", new BigDecimal("100.00"), usersCurrentAccount);
		
		ResultActions result = this.mockMvc.perform(patch(CURRENT_ACCOUNT_ROOT_URI + "/Withdrawl")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(currentTransaction)))
		.andExpect(status().isOk());
	
		Assertions.assertNotEquals(usersCurrentAccount, result.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void testGetTransactionsWorks() {
		List<CurrentTransaction> currentTransactions = currentAccountService.findCurrentTransaction(1);
		Assertions.assertNotNull(currentTransactions);
	}

}
