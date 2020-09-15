package com.fdm.Bank.Controllers;

import com.fdm.Bank.Models.CurrentAccount;
import com.fdm.Bank.Models.CurrentTransaction;
import com.fdm.Bank.Models.TransactionsRequest;
import com.fdm.Bank.Services.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    CurrentAccountService currentAccountService;

    @PostMapping("/transactions")
    public ResponseEntity<List<CurrentTransaction>> getTransactions(@RequestBody TransactionsRequest transactionRequest){
        List<CurrentTransaction> transactions = currentAccountService.findCurrentTransaction(transactionRequest.getTransactionRequestId());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
