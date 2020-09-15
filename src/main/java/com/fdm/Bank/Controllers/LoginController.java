package com.fdm.Bank.Controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.fdm.Bank.Models.LoginRequest;
import com.fdm.Bank.Models.NewUserRequest;
import com.fdm.Bank.Models.SavingsAccount;
import com.fdm.Bank.Models.User;
import com.fdm.Bank.Services.UserService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("/LoginUser")
	public User loginUser(@RequestBody LoginRequest loginRequest) {
		return userService.signin(loginRequest.getEmail(), loginRequest.getPassword()).orElseThrow(()-> 
				new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
	}
	
	@GetMapping("/AllUsers")
	public ResponseEntity<List<User>> allUsers() {
		List<User> users = userService.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
	
//	@PostMapping("/RegisterUser")
//	@ResponseStatus(HttpStatus.CREATED)
//	public User registerUser(@RequestBody NewUserRequest newUserRequest){
//		return userService.signup(newUserRequest.getFirstName(), newUserRequest.getLastName(), newUserRequest.getEmail(), newUserRequest.getPassword()).orElseThrow(() ->
//			new HttpServerErrorException(HttpStatus.BAD_REQUEST, "USER ALREADY EXISTS"));
//	}
	
	@PostMapping("/RegisterUser")
	@ResponseStatus(HttpStatus.CREATED)
	public Optional<User> registerUser(@RequestBody NewUserRequest newUserRequest){
		return userService.signup(newUserRequest.getFirstName(), newUserRequest.getLastName(), newUserRequest.getEmail(), newUserRequest.getPassword());
	}
	
	@GetMapping("/Details/{email}")
	public ResponseEntity<List> userDetails(@PathVariable("email") String email) {
		Optional<User> user = userService.findByEmail(email);
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(user.get().getUserAccount(), HttpStatus.OK);
	}

	
	

}