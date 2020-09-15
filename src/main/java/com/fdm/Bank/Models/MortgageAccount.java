package com.fdm.Bank.Models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name ="mortgageaccount")
public class MortgageAccount extends BankAccount{
	
	@Column
	private BigDecimal contractLength;
	
	@Column
	private String nextPaymentDate;
	
	@Column
	private BigDecimal nextPaymentAmount;

	public MortgageAccount() {
		super();
	}

	
	public MortgageAccount(String accountName, BigDecimal balance, BigDecimal interestRate, BigDecimal contractLength, String nextPaymentDate, BigDecimal nextPaymentAmount) {
		super(accountName, balance, interestRate);
		this.contractLength = contractLength;
		this.nextPaymentDate = nextPaymentDate;
		this.nextPaymentAmount = nextPaymentAmount;
	}


	public BigDecimal getContractLength() {
		return contractLength;
	}


	public void setContractLength(BigDecimal contractLength) {
		this.contractLength = contractLength;
	}


	public String getNextPaymentDate() {
		return nextPaymentDate;
	}


	public void setNextPaymentDate(String nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}


	public BigDecimal getNextPaymentAmount() {
		return nextPaymentAmount;
	}


	public void setNextPaymentAmount(BigDecimal nextPaymentAmount) {
		this.nextPaymentAmount = nextPaymentAmount;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contractLength == null) ? 0 : contractLength.hashCode());
		result = prime * result + ((nextPaymentAmount == null) ? 0 : nextPaymentAmount.hashCode());
		result = prime * result + ((nextPaymentDate == null) ? 0 : nextPaymentDate.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MortgageAccount other = (MortgageAccount) obj;
		if (contractLength == null) {
			if (other.contractLength != null)
				return false;
		} else if (!contractLength.equals(other.contractLength))
			return false;
		if (nextPaymentAmount == null) {
			if (other.nextPaymentAmount != null)
				return false;
		} else if (!nextPaymentAmount.equals(other.nextPaymentAmount))
			return false;
		if (nextPaymentDate == null) {
			if (other.nextPaymentDate != null)
				return false;
		} else if (!nextPaymentDate.equals(other.nextPaymentDate))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return super.toString() + "MortgageAccount [contractLength=" + contractLength + ", nextPaymentDate=" + nextPaymentDate
				+ ", nextPaymentAmount=" + nextPaymentAmount + "]";
	}


	
	

}
