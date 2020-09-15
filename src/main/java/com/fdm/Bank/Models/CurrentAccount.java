package com.fdm.Bank.Models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name ="currentaccount")
public class CurrentAccount extends BankAccount{
	
	@Column
	private BigDecimal overdraftAmount;
	
	@OneToMany(mappedBy = "currentAccount", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonIgnore
	private List<CurrentTransaction> currentTransactionList;

	public CurrentAccount() {
		super();
	}

	public CurrentAccount(String accountName, BigDecimal balance, BigDecimal interestRate, BigDecimal overdraftAmount) {
		super(accountName, balance, interestRate);
		this.overdraftAmount = overdraftAmount;
		this.currentTransactionList = new ArrayList<CurrentTransaction>();
	}

	public BigDecimal getOverdraftAmount() {
		return overdraftAmount;
	}

	public void setOverdraftAmount(BigDecimal overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}

	public List<CurrentTransaction> getCurrentTransactionList() {
		return currentTransactionList;
	}

	public void setCurrentTransactionList(List<CurrentTransaction> currentTransactionList) {
		this.currentTransactionList = currentTransactionList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currentTransactionList == null) ? 0 : currentTransactionList.hashCode());
		result = prime * result + ((overdraftAmount == null) ? 0 : overdraftAmount.hashCode());
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
		CurrentAccount other = (CurrentAccount) obj;
		if (currentTransactionList == null) {
			if (other.currentTransactionList != null)
				return false;
		} else if (!currentTransactionList.equals(other.currentTransactionList))
			return false;
		if (overdraftAmount == null) {
			if (other.overdraftAmount != null)
				return false;
		} else if (!overdraftAmount.equals(other.overdraftAmount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString()+"CurrentAccount [overdraftAmount=" + overdraftAmount + ", currentTransactionList="
				+ currentTransactionList + "]";
	}
	
	
	
	
}


	