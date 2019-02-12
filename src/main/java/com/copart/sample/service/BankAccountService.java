package com.copart.sample.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.copart.sample.model.BankAccount;
import com.copart.sample.reponses.CustomResponse;
import com.copart.sample.repositories.BankAccountRepository;
import com.copart.sample.utils.BankAccountRequestValidation;

@Service
public class BankAccountService {

	@Autowired
	BankAccountRequestValidation bankAccountRequestValidation;
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	
	@SuppressWarnings("unused")
	public ResponseEntity<Object> findAll() {
		Iterable<BankAccount> res = bankAccountRepository.findByIsDeleted(false);
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
	
	public ResponseEntity<Object> findById(Long accountId) {
	
		Optional<BankAccount> res = bankAccountRepository.findByAccountNumberAndIsDeleted(accountId, false);
		if(res.isPresent())
			return new ResponseEntity<>(res.get(), HttpStatus.OK);
		return new ResponseEntity<>(new CustomResponse("No Account exists for account id: " + accountId, 404), HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Object> addOne(BankAccount bankAccount) {
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
		return new ResponseEntity<>(new CustomResponse("Bank Account Successfully Created", 201), HttpStatus.CREATED);
	}

	public ResponseEntity<Object> updateById(Long accountId, BankAccount bankAccount) {
		
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

	public ResponseEntity<Object> deleteById(Long accountId) {
		
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
}
