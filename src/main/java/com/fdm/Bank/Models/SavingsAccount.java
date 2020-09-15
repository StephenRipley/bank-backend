package com.fdm.Bank.Models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name ="savingsaccount")
public class SavingsAccount extends BankAccount{
	
	@Column
	public String accessType;

	public SavingsAccount(String accountName, BigDecimal balance, BigDecimal interestRate, String accessType) {
		super(accountName, balance, interestRate);
		this.accessType = accessType;
	}
	
	public SavingsAccount() {
		super();
	}


	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accessType == null) ? 0 : accessType.hashCode());
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
		SavingsAccount other = (SavingsAccount) obj;
		if (accessType == null) {
			if (other.accessType != null)
				return false;
		} else if (!accessType.equals(other.accessType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString()+"SavingsAccount [accessType=" + accessType + "]";
	}
	

	
}