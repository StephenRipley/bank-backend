package com.fdm.Bank.Models;

import java.math.BigDecimal;
import java.util.Objects;

public class LoanDetails {

	BigDecimal yearlyWage;
	BigDecimal loan;
	ContractType contractType;
	String loanAffordable;
	BigDecimal calculatedLoanAmount;
	Boolean netWage;
	StudentLoan studentLoan;
	
	public LoanDetails(BigDecimal yearlyWage, BigDecimal loan, ContractType contractType, String loanAffordable,
			BigDecimal calculatedLoanAmount, Boolean netWage, StudentLoan studentLoan) {
		super();
		this.yearlyWage = yearlyWage;
		this.loan = loan;
		this.contractType = contractType;
		this.loanAffordable = loanAffordable;
		this.calculatedLoanAmount = calculatedLoanAmount;
		this.netWage = netWage;
		this.studentLoan = studentLoan;
	}

	public BigDecimal getYearlyWage() {
		return yearlyWage;
	}

	public void setYearlyWage(BigDecimal yearlyWage) {
		this.yearlyWage = yearlyWage;
	}

	public BigDecimal getLoan() {
		return loan;
	}

	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}

	public ContractType getContractType() {
		return contractType;
	}

	public void setContractType(ContractType contractType) {
		this.contractType = contractType;
	}

	public String getLoanAffordable() {
		return loanAffordable;
	}

	public void setLoanAffordable(String loanAffordable) {
		this.loanAffordable = loanAffordable;
	}

	public BigDecimal getCalculatedLoanAmount() {
		return calculatedLoanAmount;
	}

	public void setCalculatedLoanAmount(BigDecimal calculatedLoanAmount) {
		this.calculatedLoanAmount = calculatedLoanAmount;
	}

	public Boolean getNetWage() {
		return netWage;
	}

	public void setNetWage(Boolean netWage) {
		this.netWage = netWage;
	}

	public StudentLoan getStudentLoan() {
		return studentLoan;
	}

	public void setStudentLoan(StudentLoan studentLoan) {
		this.studentLoan = studentLoan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calculatedLoanAmount == null) ? 0 : calculatedLoanAmount.hashCode());
		result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
		result = prime * result + ((loan == null) ? 0 : loan.hashCode());
		result = prime * result + ((loanAffordable == null) ? 0 : loanAffordable.hashCode());
		result = prime * result + ((netWage == null) ? 0 : netWage.hashCode());
		result = prime * result + ((studentLoan == null) ? 0 : studentLoan.hashCode());
		result = prime * result + ((yearlyWage == null) ? 0 : yearlyWage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanDetails other = (LoanDetails) obj;
		if (calculatedLoanAmount == null) {
			if (other.calculatedLoanAmount != null)
				return false;
		} else if (!calculatedLoanAmount.equals(other.calculatedLoanAmount))
			return false;
		if (contractType != other.contractType)
			return false;
		if (loan == null) {
			if (other.loan != null)
				return false;
		} else if (!loan.equals(other.loan))
			return false;
		if (loanAffordable == null) {
			if (other.loanAffordable != null)
				return false;
		} else if (!loanAffordable.equals(other.loanAffordable))
			return false;
		if (netWage == null) {
			if (other.netWage != null)
				return false;
		} else if (!netWage.equals(other.netWage))
			return false;
		if (studentLoan != other.studentLoan)
			return false;
		if (yearlyWage == null) {
			if (other.yearlyWage != null)
				return false;
		} else if (!yearlyWage.equals(other.yearlyWage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoanDetails [yearlyWage=" + yearlyWage + ", loan=" + loan + ", contractType=" + contractType
				+ ", loanAffordable=" + loanAffordable + ", calculatedLoanAmount=" + calculatedLoanAmount + ", netWage="
				+ netWage + ", studentLoan=" + studentLoan + "]";
	}
	
}

