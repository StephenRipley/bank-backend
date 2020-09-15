package com.fdm.Bank.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name="currenttransaction")
public class CurrentTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currenttransaction_gen")
	@SequenceGenerator(name="currenttransaction_gen", sequenceName ="CURRENTTRANSACTION_SEQ", allocationSize=1)
	private long currentTransactionId;
	
	@Column
	private LocalDate date;
	
	@Column
	private String description;
	
	@Column
	private String type;
	
	@Column
	private String status;
	
	@Column
	private BigDecimal amount;
	
	@ManyToOne
	@JoinColumn(name="accountId")
	private CurrentAccount currentAccount;

	public CurrentTransaction() {
		super();
	}

	public CurrentTransaction(LocalDate date, String description, String type, String status, BigDecimal amount,
			CurrentAccount currentAccount) {
		super();
		this.date = date;
		this.description = description;
		this.type = type;
		this.status = status;
		this.amount = amount;
		this.currentAccount = currentAccount;
	}

	public long getCurrentTransactionId() {
		return currentTransactionId;
	}

	public void setCurrentTransactionId(long currentTransactionId) {
		this.currentTransactionId = currentTransactionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public CurrentAccount getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(CurrentAccount currentAccount) {
		this.currentAccount = currentAccount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currentAccount == null) ? 0 : currentAccount.hashCode());
		result = prime * result + (int) (currentTransactionId ^ (currentTransactionId >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CurrentTransaction other = (CurrentTransaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (currentAccount == null) {
			if (other.currentAccount != null)
				return false;
		} else if (!currentAccount.equals(other.currentAccount))
			return false;
		if (currentTransactionId != other.currentTransactionId)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CurrentTransaction [currentTransactionId=" + currentTransactionId + ", date=" + date + ", description="
				+ description + ", type=" + type + ", status=" + status + ", amount=" + amount + "]";
	}
	
	
	
	
	
	

}
