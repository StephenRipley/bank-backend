package com.fdm.Bank.Repository;

import java.util.Optional;

import com.fdm.Bank.Models.User;

public interface AccountServiceRepository<T> {
	
	void save(T t);
	
	void addAccount(T t, Optional<User> user);

}
