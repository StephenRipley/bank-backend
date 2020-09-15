package com.fdm.Bank.Repository;

import com.fdm.Bank.Models.CurrentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrentTransactionDao extends JpaRepository<CurrentTransaction, Long> {

    @Query(value ="SELECT * FROM CURRENTTRANSACTION WHERE ACCOUNTID = :accountId", nativeQuery = true)
    List<CurrentTransaction> findCurrentTransactions(@Param("accountId")Long accountId);
}
