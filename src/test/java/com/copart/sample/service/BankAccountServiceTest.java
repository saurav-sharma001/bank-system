package com.copart.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.copart.sample.model.BankAccount;
import com.copart.sample.repositories.BankAccountRepository;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankAccountServiceTest {

	@InjectMocks
	private BankAccountService bankAccountService = new BankAccountService();
	
	@Mock
	private BankAccountRepository bankAccountRepository = Mockito.mock(BankAccountRepository.class);
	
	private List<BankAccount> bankAccounts;
	
	@Before
	public void init() {
		bankAccounts = new ArrayList<>();
		
		BankAccount ba1 = new BankAccount("Saurav", "Sharma", "Saurav.Sharma@Copart.com", null);
		BankAccount ba2 = new BankAccount("Vishrut", "Sharma", "Gaurav.Sharma@Copart.com", null);
		
		bankAccounts.add(ba1);
		bankAccounts.add(ba2);
	}
	
	@When("^the client send a request to retrive all accounts")
	public void client_sends_request_all_account() throws Throwable {
		
		Mockito
			.when(bankAccountRepository.findByIsDeleted(false))
			.thenReturn(bankAccounts);
	}
	
	@Then("^the client receive get list of all accounts")
	public void client_receive_status_code() {
		
	}
}
