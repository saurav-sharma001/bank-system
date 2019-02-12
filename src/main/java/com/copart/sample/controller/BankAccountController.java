package com.copart.sample.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.copart.sample.model.BankAccount;
import com.copart.sample.reponses.CustomResponse;
import com.copart.sample.repositories.BankAccountRepository;
import com.copart.sample.utils.BankAccountRequestValidation;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

	@Autowired
	BankAccountRequestValidation bankAccountRequestValidation;
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "", method = RequestMethod.GET)
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

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Object> addAnAccount(@RequestBody BankAccount bankAccount) {
		
		String validateMessage = bankAccountRequestValidation.validateAddBankAccountRequest(bankAccount);		
		if(validateMessage.length() > 2) {
			return new ResponseEntity<>(new CustomResponse(validateMessage, 400), HttpStatus.BAD_REQUEST);
		}
		
		// Setting Default Values
		bankAccount.setIsDeleted(false);
		bankAccount.setCreatedTimestamp();
		bankAccount.setUpdatedTimestamp();
		if(bankAccount.getBranchName() == null || bankAccount.getBranchName().length() < 0) {
			bankAccount.setBranchName("Dallas");
		}
		
		bankAccountRepository.save(bankAccount);
		return new ResponseEntity<>(new CustomResponse("Created Bank Account", 201), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<Object> fetchAnAccount(@PathVariable Long accountId) {
		
		Optional<BankAccount> res = bankAccountRepository.findById(accountId);
		if(res.isPresent())
			return new ResponseEntity<>(res.get(), HttpStatus.OK);
		return new ResponseEntity<>(new CustomResponse("No Account exists for account id: " + accountId, 404), HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateAnAccount(@PathVariable Long accountId, @RequestBody BankAccount bankAccount) {
		
		Optional<BankAccount> res = bankAccountRepository.findById(accountId);
		if(!res.isPresent()) {			
			return new ResponseEntity<>(new CustomResponse("No Account exists for account id: " + accountId, 404), HttpStatus.NOT_FOUND);
		}
		
		String validateMessage = bankAccountRequestValidation.validateUpdateBankAccountRequest(bankAccount);		
		if(validateMessage.length() > 2) {
			return new ResponseEntity<>(new CustomResponse(validateMessage, 400), HttpStatus.BAD_REQUEST);
		}
		
		BankAccount bankAccountToUpdate = res.get();
		bankAccountToUpdate.setUpdatedTimestamp();
		bankAccountToUpdate.setPhone(bankAccount.getPhone());
		
		bankAccountRepository.save(bankAccountToUpdate);
		return new ResponseEntity<>(bankAccountToUpdate, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAnAccount(@PathVariable Long accountId) {
		
		Optional<BankAccount> res = bankAccountRepository.findById(accountId);
		if(!res.isPresent()) {
			return new ResponseEntity<>(new CustomResponse("No Account exists for account id: " + accountId, 404), HttpStatus.NOT_FOUND);
		}
		BankAccount bankAccountToUpdate = res.get();
		bankAccountToUpdate.setIsDeleted(true);
		bankAccountToUpdate.setUpdatedTimestamp();
		bankAccountRepository.save(bankAccountToUpdate);
		return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/test")
	public String testAPI() {
		return "API is working";
	}
}
