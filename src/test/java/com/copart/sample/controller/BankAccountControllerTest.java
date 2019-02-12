package com.copart.sample.controller;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.copart.sample.repositories.BankAccountRepository;
import com.copart.sample.service.BankAccountService;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="classpath:features/account_creation.feature")
public class BankAccountControllerTest {

	@InjectMocks
	private BankAccountService bankAccountService = new BankAccountService();
	
	@Mock
	private BankAccountRepository bankAccountRepository = Mockito.mock(BankAccountRepository.class);
	
	@When("^the client send a request to retrive all accounts")
	public void client_sends_request_all_account() throws Throwable {
		
		Mockito
			.when(bankAccountRepository.findByIsDeleted(false))
			.thenReturn(null);
	}
}
