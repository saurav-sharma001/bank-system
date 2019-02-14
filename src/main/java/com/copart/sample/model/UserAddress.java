package com.copart.sample.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_address")
public class UserAddress implements Serializable {

	private static final long serialVersionUID = -5719484975660950773L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Long addressID;
	@Column(name = "street")
	private String street;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "zipcode")
	private String zipcode;
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	@Column(name = "created_timestamp")
	private Timestamp createdTimestamp;
	@Column(name = "updated_timestamp")
	private Timestamp updatedTimestamp;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_number", nullable = false)
	@JsonIgnore
	private BankAccount accountNumber;
	
	protected UserAddress() {
		
	}
	
	public UserAddress(String street, String city, String state, String country, String zipcode, BankAccount accountNumber) {
		this.street = street;
		this.state = state;
		this.city = city;
		this.country = country;
		this.zipcode = zipcode;
		this.isDeleted = false;
		this.createdTimestamp = new Timestamp(System.currentTimeMillis());
		this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
		this.accountNumber = accountNumber;
	}
	
	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(Long addressID) {
		this.addressID = addressID;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	
	public BankAccount getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BankAccount accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@Override
	public String toString() {
		return "{\"street\":" + this.street + ",\"city\":\"" + this.city + "\", \"state\": \"" + this.state + "\", \"country\": \"" + this.country + "\", \"createdTimestamp\": \"" + this.createdTimestamp + "\"}";
	}
}
