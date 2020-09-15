package com.fdm.Bank.Models;

public enum  StudentLoan {
	NONE("No Loan"),
    PLAN1("Repayment Plan 1"),
    PLAN2("Repayment Plan 2"),
    POSTGRADUATE("Postgraduate Loan");

    private String name;

    StudentLoan(String studentLoan) {
        name = studentLoan;
    }

    public String getName() {
        return name;
    }
}


