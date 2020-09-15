package com.fdm.Bank.Repository;

import javax.transaction.Transactional;

import com.fdm.Bank.Models.CurrentTransaction;
import com.fdm.Bank.Models.SavingsAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdm.Bank.Models.CurrentAccount;

import java.util.List;

public interface CurrentAccountDao extends JpaRepository<CurrentAccount, Long>{
	
	@Query(value ="SELECT * FROM CURRENTACCOUNT WHERE USERID = :checkedUserId", nativeQuery = true)
    List<CurrentAccount> findCurrentAccountsOfSpecificUser(@Param("checkedUserId")Long checkedUserId);
	
	@Modifying
	@Query(value ="UPDATE currentaccount SET userId=:userId WHERE accountId=:accountId")
	@Transactional
	void addAccountToUser(@Param("accountId")Long accountId, @Param("userId")Long userId);

}
