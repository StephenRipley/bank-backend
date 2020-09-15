package com.fdm.Bank.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.Bank.Models.CurrentAccount;
import com.fdm.Bank.Models.CurrentTransaction;
import com.fdm.Bank.Models.User;
import com.fdm.Bank.Repository.AccountServiceRepository;
import com.fdm.Bank.Repository.CurrentAccountDao;
import com.fdm.Bank.Repository.CurrentTransactionDao;

@Service
public class CurrentAccountService implements AccountServiceRepository<CurrentAccount>{
	
	@Autowired
	CurrentAccountDao currentAccountDao;

	@Autowired
	CurrentTransactionDao currentTransactionDao;
	
	@Autowired
	UserService userService;
	
	public void save(CurrentAccount currentAcount) {
		currentAccountDao.save(currentAcount);
	}
	
	public Optional<CurrentAccount> findById(long accountId) {
		return currentAccountDao.findById(accountId);
	}
	
	public void addAccount(CurrentAccount currentAccount, Optional<User> user) {
		long currentAccountId = currentAccount.getAccountId();
		long userId = user.get().getUserId();
		
		currentAccountDao.addAccountToUser(currentAccountId, userId);
	}

	public CurrentAccount deposit(CurrentTransaction currentTransaction) {
		CurrentAccount currentAccount = currentAccountDao.findById(currentTransaction.getCurrentAccount().getAccountId()).get();
		BigDecimal currentbalance = currentAccount.getBalance();
		BigDecimal newBalance = currentbalance.add(currentTransaction.getAmount());
		currentAccount.setBalance(newBalance);
		currentAccount.getCurrentTransactionList().add(currentTransaction);
		currentAccountDao.save(currentAccount);
		return currentAccount;
	}

	public CurrentAccount withdrawl(CurrentTransaction currentTransaction) {
		CurrentAccount currentAccount = currentAccountDao.findById(currentTransaction.getCurrentAccount().getAccountId()).get();
		BigDecimal currentBalance = currentAccount.getBalance();
		BigDecimal newBalance = currentBalance.subtract(currentTransaction.getAmount());
		currentAccount.setBalance(newBalance);
		currentAccount.getCurrentTransactionList().add(currentTransaction);
		currentAccountDao.save(currentAccount);
		return currentAccount;
	}

	public List<CurrentTransaction> findCurrentTransaction(long accountId){
		return currentTransactionDao.findCurrentTransactions(accountId);
	}

	public CurrentAccount createCurrentAccount() {
		CurrentAccount newCurrentAccount = new CurrentAccount("Current Account", new BigDecimal("00.00"), new BigDecimal("0.0"), new BigDecimal("00.00"));
		currentAccountDao.save(newCurrentAccount);
		return newCurrentAccount;
	}
}
