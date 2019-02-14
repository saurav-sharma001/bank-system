package com.copart.sample.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.copart.sample.CopartBusinessAccountCucumberIntegrationTest;
import com.copart.sample.model.BankAccount;
import com.copart.sample.repositories.BankAccountRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BankAccountServiceTest extends CopartBusinessAccountCucumberIntegrationTest {

	@InjectMocks
	private BankAccountService bankAccountService = new BankAccountService();
	
	@Mock
	private BankAccountRepository bankAccountRepository = Mockito.mock(BankAccountRepository.class);
		
	private static List<BankAccount> bankAccounts;
	private BankAccount sampleBankAccount;
	private ResponseEntity<Object> response;
	
	@BeforeClass
	public void init() {
//		bankAccounts = new ArrayList<>();
//		BankAccount ba1 = new BankAccount(1L, "Saurav", "Sharma", "Saurav.Sharma@Copart.com", null);
//		BankAccount ba2 = new BankAccount(2L, "Vishrut", "Sharma", "Gaurav.Sharma@Copart.com", null);
//		bankAccounts.add(ba1);
//		bankAccounts.add(ba2);
	}
	
	@Given("^account detail: ([\\w: ,@.]+)$")
	public void create_bank_account(String detail) {
		try {
			String[] keys = detail.split(",");
			Long accountNumber = null;
			String firstName = null, lastName = null, email = null, phone = null;
			for(String key : keys) {
				if(key.indexOf("accountNumber") != -1) {
					accountNumber = Long.valueOf(key.split(":")[1].trim());
				} else if (key.indexOf("firstName") != -1) {
					firstName = key.split(":")[1].trim();
				} else if (key.indexOf("lastName") != -1) {
					lastName = key.split(":")[1].trim();
				} else if (key.indexOf("email") != -1) {
					email = key.split(":")[1].trim();
				} else if (key.indexOf("phone") != -1) {
					phone = key.split(":")[1].trim();
				}
			}
			sampleBankAccount = new BankAccount(accountNumber, firstName, lastName, email, phone);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	@When("^the client send a request to retrieve all accounts$")
	public void client_sends_request_all_account() throws Throwable {
		bankAccounts = new ArrayList<>();
		BankAccount ba1 = new BankAccount(1L, "Saurav", "Sharma", "Saurav.Sharma@Copart.com", null);
		BankAccount ba2 = new BankAccount(2L, "Vishrut", "Sharma", "Gaurav.Sharma@Copart.com", null);
		bankAccounts.add(ba1);
		bankAccounts.add(ba2);
		
	}
	
	@When("^the client tries to create bank account")
	public void create_bank_account() throws Throwable {
		System.out.println("creating account " + sampleBankAccount);
		response = bankAccountService.addOne(sampleBankAccount);
	}
	
	@Then("^the client get total of (\\d+) accounts$")
	public void the_client_get_list_of_all_the_accounts(final int count) throws Throwable {
		assertEquals(count, bankAccounts.size());
	}
	
	@Then("^the client should receive \\\"([^\\\"]*)\\\" message$")
	public void client_response_validation(String message) {
		assertEquals(message, response.getBody().toString());
	}
}
