package com.fdm.Bank.Models;

public enum  ContractType {
    FULLTIME("Full Time"),
    PARTTIME("Part Time"),
    ZEROHOURS("Zero Hours"),
    SELFEMPLOYED("Self Employed"),
    CONTRACTOR("Contractor");

    private String name;

    ContractType(String contractType) {
        name = contractType;
    }

    public String getName() {
        return name;
    }
}


