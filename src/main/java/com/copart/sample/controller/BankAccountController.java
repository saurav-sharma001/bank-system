package com.copart.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.copart.sample.model.BankAccount;
import com.copart.sample.service.BankAccountService;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

	@Autowired
	BankAccountService bankAccountService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Object> fetchAllAccounts() {
		return bankAccountService.findAll();
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Object> addAnAccount(@RequestBody BankAccount bankAccount) {
		return bankAccountService.addOne(bankAccount);
	}
	
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<Object> fetchAnAccount(@PathVariable Long accountId) {
		return bankAccountService.findById(accountId);
	}
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateAnAccount(@PathVariable Long accountId, @RequestBody BankAccount bankAccount) {
		return bankAccountService.updateById(accountId, bankAccount);
	}
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAnAccount(@PathVariable Long accountId) {
		return bankAccountService.deleteById(accountId);
	}
	
	@RequestMapping(value = "/{accountId}/address", method = RequestMethod.GET)
	public ResponseEntity<Object> fetchAddressForAnAccount(@PathVariable Long accountId) {
		return bankAccountService.getAddressForAnAccountNumber(accountId);
	}
	
	@GetMapping("/test")
	public String testAPI() {	
		return "API is working";
	}
}
