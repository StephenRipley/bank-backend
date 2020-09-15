package com.fdm.Bank.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdm.Bank.Models.MortgageAccount;

public interface MortgageAccountDao extends JpaRepository<MortgageAccount, Long>{
	
	@Modifying
	@Query(value ="UPDATE mortgageaccount SET userId=:userId WHERE accountId=:accountId")
	@Transactional
	void addAccountToUser(@Param("accountId")Long accountId, @Param("userId")Long userId); 


}
