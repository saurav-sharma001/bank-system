package com.copart.sample.utils;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copart.sample.model.BankAccount;
import com.copart.sample.repositories.BankAccountRepository;

@Service
public class BankAccountRequestValidation {

	Pattern emailPattern, phonePattern;
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	
	@PostConstruct
	public void initialize() {
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		String PHONE_PATTERN = "^[1-9][0-9]{9}";
		emailPattern = Pattern.compile(EMAIL_PATTERN);
		phonePattern = Pattern.compile(PHONE_PATTERN);
	}
	
	public String validateAddBankAccountRequest(BankAccount bankAccount) {
		System.out.println("Bank Account detail received: " + bankAccount);
		String message = "";
		if(bankAccount.getAccountNumber() == null) {
			return("Account Number is a required field");
		} else if(bankAccount.getEmail() == null) {
			return("Email is a required field");
		} else if(bankAccount.getFirstName() == null || bankAccount.getFirstName().trim().length() < 1) {
			return("First name not provided");
		} else if(bankAccount.getLastName() == null || bankAccount.getLastName().trim().length() < 1) {
			return("Last name not provided");
		} else if(!emailPattern.matcher(bankAccount.getEmail()).matches()) {
			return("Not a valid Email");
		} else if(bankAccount.getPhone() != null && !phonePattern.matcher(bankAccount.getPhone()).matches()) {
			return("Not a valid Phone");
		}
		
		List<BankAccount> oldRec = bankAccountRepository.findByEmail(bankAccount.getEmail());
		if(oldRec.size() > 0) {
			return ("Email already exists");
		}
		return message;
	}

	public String validateUpdateBankAccountRequest(BankAccount bankAccount) {
		System.out.println("Bank Account detail received: " + bankAccount);
		String message = "";
		if(bankAccount.getPhone() == null) {
			return("No content to udpate");
		} else if(!phonePattern.matcher(bankAccount.getPhone()).matches()) {
			return("Not a valid Phone");
		}
		return message;
	}
}
