package com.fdm.Bank.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.Bank.Models.MortgageAccount;
import com.fdm.Bank.Models.SavingsAccount;
import com.fdm.Bank.Models.User;
import com.fdm.Bank.Repository.AccountServiceRepository;
import com.fdm.Bank.Repository.MortgageAccountDao;

@Service
public class MortgageAccountService implements AccountServiceRepository<MortgageAccount>{
	
	@Autowired
	MortgageAccountDao mortgageAccountDao;
	
	@Autowired
	UserService userService;
	
	public void save(MortgageAccount mortgageAccount) {
		mortgageAccountDao.save(mortgageAccount);
	}
	
	public Optional<MortgageAccount> findById(long accountId) {
		return mortgageAccountDao.findById(accountId);
	}
	
	public void addAccount(MortgageAccount mortgageAccount, Optional<User> user) {
		long mortgageAccountId = mortgageAccount.getAccountId();
		long userId = user.get().getUserId();
		
		mortgageAccountDao.addAccountToUser(mortgageAccountId, userId);
	}

}
