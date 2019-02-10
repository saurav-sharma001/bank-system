package com.copart.sample.controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.copart.sample.model.BankAccount;
import com.copart.sample.reponses.CustomResponse;
import com.copart.sample.repositories.BankAccountRepository;

@RestController
public class BankAccountController {

	Pattern pattern;
	
	@PostConstruct
	public void initialize() {
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	
	@SuppressWarnings("unused")
	@GetMapping("/accounts")
	public ResponseEntity<Object> fetchAllAccounts() {
		Iterable<BankAccount> res = bankAccountRepository.findAll();
		int count = 0;
		for(BankAccount b : res) {
			count++;
		}
		if(count > 0) {
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponse("No Account returned.", 404), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/accounts")
	public ResponseEntity<Object> addAnAccount(@RequestBody BankAccount bankAccount) {
		
		// Setting Default Values
		bankAccount.setIsDeleted(false);
		bankAccount.setCreatedTimestamp();
		bankAccount.setUpdatedTimestamp();
		System.out.println("Bank Account detail received: " + bankAccount);

		Boolean validBody = true;
		String message = null;
		if(bankAccount.getEmail() == null) {
			validBody = false;
			message = "Email is a required field";
		} else if(bankAccount.getFirstName() == null || bankAccount.getFirstName().trim().length() < 1) {
			validBody = false;
			message = "First name not provided";
		} else if(bankAccount.getLastName() == null || bankAccount.getLastName().trim().length() < 1) {
			validBody = false;
			message = "Last name not provided";
		}  else if(!pattern.matcher(bankAccount.getEmail()).matches()) {
			validBody = false;
			message = "Not a valid Email";
		} 
		if(!validBody) {
			return new ResponseEntity<>(new CustomResponse(message, 400), HttpStatus.BAD_REQUEST);
		}
		List<BankAccount> oldRec = bankAccountRepository.findByEmail(bankAccount.getEmail());
		
		if(oldRec.size() > 0) {
			return new ResponseEntity<>(new CustomResponse("Email already exists", 400), HttpStatus.BAD_REQUEST);
		}
		
		bankAccountRepository.save(bankAccount);
		return new ResponseEntity<>(new CustomResponse("Created Bank Account", 201), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/accounts/{accountId}")
	public ResponseEntity<Object> fetchAnAccount(@PathVariable Long accountId) {
		Optional<BankAccount> res = bankAccountRepository.findById(accountId);
		if(res.isPresent())
			return new ResponseEntity<>(res.get(), HttpStatus.OK);
		return new ResponseEntity<>(new CustomResponse("No Account exists for account id: " + accountId, 404), HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/accounts/{accountId}")
	public ResponseEntity<Object> updateAnAccount(@PathVariable Long accountId) {
		Optional<BankAccount> res = bankAccountRepository.findById(accountId);
		if(!res.isPresent()) {			
			return new ResponseEntity<>(new CustomResponse("No Account exists for account id: " + accountId, 404), HttpStatus.NOT_FOUND);
		}
		BankAccount bankAccountToUpdate = res.get();
		bankAccountToUpdate.setUpdatedTimestamp();
		bankAccountRepository.save(bankAccountToUpdate);
		return new ResponseEntity<>(bankAccountToUpdate, HttpStatus.OK);
	}
	
	@DeleteMapping("/accounts/{accountId}")
	public ResponseEntity<Object> deleteAnAccount(@PathVariable Long accountId) {
		Optional<BankAccount> res = bankAccountRepository.findById(accountId);
		if(!res.isPresent()) {
			return new ResponseEntity<>(new CustomResponse("No Account exists for account id: " + accountId, 404), HttpStatus.NOT_FOUND);
		}
		BankAccount bankAccountToUpdate = res.get();
		bankAccountToUpdate.setIsDeleted(true);
		bankAccountToUpdate.setUpdatedTimestamp();
		bankAccountRepository.save(bankAccountToUpdate);
		return new ResponseEntity<>(bankAccountToUpdate, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String testAPI() {
		return "API is working";
	}
}
