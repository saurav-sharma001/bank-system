package com.copart.sample.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {
	
	private static final long serialVersionUID = 4337784876596873493L;
	@Id
	@Column(name = "account_number")
	private Long accountNumber;
	
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email")
	private String email;
	
	@Column(name = "branch_name")
	private String branchName;
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	@Column(name = "created_timestamp")
	private Timestamp createdTimestamp;
	@Column(name = "updated_timestamp")
	private Timestamp updatedTimestamp;
	
	@OneToMany(mappedBy = "accountNumber")
	private Set<UserAddress> address = new HashSet<>();

	public BankAccount() {
	}
	
	public BankAccount(Long accountNumber, String firstName, String lastName, String email, String phone) {
		this.accountNumber = accountNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.isDeleted = false;
		this.createdTimestamp = new Timestamp(System.currentTimeMillis());
		this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountId) {
		this.accountNumber = accountId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public Boolean getIsDeleted() {
//		return isDeleted;
//	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

//	public Timestamp getCreatedTimestamp() {
//		return createdTimestamp;
//	}

	public void setCreatedTimestamp() {
		this.createdTimestamp = new Timestamp(System.currentTimeMillis());
	}

//	public Timestamp getUpdatedTimestamp() {
//		return updatedTimestamp;
//	}

	public void setUpdatedTimestamp() {
		this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
	}

//	public String getName() {
//		return this.firstName + " " + this.lastName;
//	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(Set<UserAddress> address) {
		this.address = address;
	}
	
	public Set<UserAddress> getAddress() {
		return this.address;
	}
	
	
	@Override
	public String toString() {
		return "{\"account_number\":" + this.accountNumber + ",\"name\":\"" + this.firstName + " " + this.lastName + "\", \"email\": \"" + this.email + "\", \"address\": \"" + this.address + "\", \"createdTimestamp\": \"" + this.createdTimestamp + "\"}";
	}
}
