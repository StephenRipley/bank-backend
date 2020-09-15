package com.fdm.Bank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdm.Bank.Models.User;
import com.fdm.Bank.Services.UserService;

@AutoConfigureMockMvc
@SpringBootTest
public class UserTests {

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserService userService;

	MockMvc mockMvc;

	MockHttpSession session;

	final static String LOGIN_ROOT_URI = "/login";

	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession()).build();
	}

	@AfterEach
	public void test() {
		this.session = null;
		this.mockMvc = null;
	}

	@Test
	public void testToCreateANewUser() {
		User user = new User("mark", "smith", "mark@email.com", "password");
		userService.save(user);
		Assertions.assertNotNull(user);
		List<User> allusers = userService.findAll();
		Assertions.assertNotNull(allusers);
	}

	@Test
	public void testThatAUserCanBeFoundById() {
		Optional<User> user = userService.findById(1L);
		Optional<User> userFromDB = userService.findById(user.get().getUserId());
		Assertions.assertEquals(user.get().getUserId(), userFromDB.get().getUserId());
	}

	@Test
	public void testThatAUserCanBeFoundByEmail() {
		Optional<User> user = userService.findByEmail("woodn@gmail.com");
		Optional<User> userFromDB = userService.findByEmail(user.get().getEmail());
		Assertions.assertEquals(user.get().getEmail(), userFromDB.get().getEmail());
	}

	@Test
	public void testThatAUserCanBeFoundByEmailAndPassword() {
		Optional<User> user = userService.findByEmailAndPassword("woodn@gmail.com", "pog");
		Optional<User> userFromDB = userService.findByEmailAndPassword(user.get().getEmail(), user.get().getPassword());
		Assertions.assertEquals(user.get().getEmail(), userFromDB.get().getEmail());
	}

	@Test
	public void getAllUsers() throws Exception {
		ResultActions result = this.mockMvc.perform(get(LOGIN_ROOT_URI + "/AllUsers").session(session))
				.andExpect(status().isOk());

		Assertions.assertNotNull(result);
	}

	@Test
	public void ThatAUserCanSignIn() {
		User userFromId = userService.findById(1).get();
		String email = userFromId.getEmail();
		String password = userFromId.getPassword();
		User userFromSignIn = userService.signin(email, password).get();
		Assertions.assertNotNull(userFromSignIn);
	}

	@Test
	public void ThatAUserCannotSignInWithIncorrectDetails() {
		Optional<User> userFromSignIn = userService.signin("a", "password");
		Assertions.assertTrue(userFromSignIn.isEmpty());

	}

	@Test
	public void signIn() throws Exception {
		Optional<User> user = userService.findById(1L);

		this.mockMvc.perform(post(LOGIN_ROOT_URI + "/LoginUser").session(session)
				.contentType("application/json").content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void registerNewUser() throws Exception {
		int numberBeforeAdding = userService.findAll().size();
		User newUser = new User("Frodo", "Baggins", "oneRing@mordor.com", "Smeagol");

		ResultActions result = this.mockMvc.perform(post(LOGIN_ROOT_URI + "/RegisterUser").session(session)
				.contentType("application/json").content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isCreated());

		int numberAfterAdding = userService.findAll().size();
		Assertions.assertEquals(numberBeforeAdding + 1, numberAfterAdding);
	}
	
	@Test
	public void confirmThatUserID1HasACurrentAccount() {
		Assertions.assertTrue(userService.checkForCurrentAccount(1));
	}
	
	@Test
	public void confirmThatUserID3DoesNotHaveACurrentAccount() {
		Assertions.assertFalse(userService.checkForCurrentAccount(3));
	}
	
	@Test
	public void confirmThatUserID1HasASavingsAccount() {
		Assertions.assertTrue(userService.checkForSavingsAccount(1));
	}
	
	@Test
	public void confirmThatUserID2DoesNotHaveASavingsAccount() {
		Assertions.assertFalse(userService.checkForSavingsAccount(2));
	}

}
