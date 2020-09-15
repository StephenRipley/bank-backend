package com.fdm.Bank.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdm.Bank.Models.CurrentTransaction;
import com.fdm.Bank.Models.SavingsAccount;

public interface SavingsAccountDao extends JpaRepository<SavingsAccount, Long> {
	
	@Query(value ="SELECT * FROM SAVINGSACCOUNT WHERE USERID = :checkedUserId", nativeQuery = true)
    List<SavingsAccount> findSavingsAccountsOfSpecificUser(@Param("checkedUserId")Long checkedUserId);
	
	@Modifying
	@Query(value ="UPDATE savingsaccount SET userId=:userId WHERE accountId=:accountId")
	@Transactional
	void addAccountToUser(@Param("accountId")Long accountId, @Param("userId")Long userId);

}
