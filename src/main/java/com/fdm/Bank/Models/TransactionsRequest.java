package com.fdm.Bank.Models;

public class TransactionsRequest {

    private long transactionRequestId;

    public TransactionsRequest() {
    }

    public TransactionsRequest(long transactionRequestId) {
        this.transactionRequestId = transactionRequestId;
    }

    public long getTransactionRequestId() {
        return transactionRequestId;
    }

    public void setTransactionRequestId(long transactionRequestId) {
        this.transactionRequestId = transactionRequestId;
    }
}
