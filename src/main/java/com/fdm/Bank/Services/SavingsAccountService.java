package com.fdm.Bank.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.Bank.Models.SavingsAccount;
import com.fdm.Bank.Models.User;
import com.fdm.Bank.Repository.AccountServiceRepository;
import com.fdm.Bank.Repository.SavingsAccountDao;
	
@Service
public class SavingsAccountService implements AccountServiceRepository<SavingsAccount>{
		
	@Autowired
	SavingsAccountDao savingsAccountDao;
	
	@Autowired
	UserService userService;
	
	public void save(SavingsAccount savingsAccount) {
		savingsAccountDao.save(savingsAccount);
	}
	
	public Optional<SavingsAccount> findById(long accountId) {
		return savingsAccountDao.findById(accountId);
	}
	
	public void addAccount(SavingsAccount savingsAccount, Optional<User> user) {
		long savingsAccountId = savingsAccount.getAccountId();
		long userId = user.get().getUserId();
		
		savingsAccountDao.addAccountToUser(savingsAccountId, userId);
	}
}
