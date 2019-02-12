package com.copart.sample.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.copart.sample.model.BankAccount;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="classpath:features/account_creation.feature")
@SpringBootTest
public class BankAccountRepositoryTest {

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Before
	public void setUp() throws Exception {
		
		System.out.println("---------- Running Test Cases -----------");
		BankAccount ba1 = new BankAccount(1L, "Saurav", "Sharma", "Saurav.Sharma@Copart.com", null);
		BankAccount ba2 = new BankAccount(2L, "Vishrut", "Sharma", "Gaurav.Sharma@Copart.com", null);
		
		assertNull(ba1.getAccountNumber());
		assertNull(ba2.getAccountNumber());
		
		this.bankAccountRepository.save(ba1);
		this.bankAccountRepository.save(ba2);
		
		assertNotNull(ba1.getAccountNumber());
		assertNotNull(ba2.getAccountNumber());
	}
	
	@Test
	public void testFetchData() {
		
		System.out.println("Fetching data by Last Name");
		List<BankAccount> ba3 = bankAccountRepository.findByLastName("Sharma");
		assertNotNull(ba3);
		System.out.println("Records returned: " + ba3.size());
		for(BankAccount b : ba3) {
			assertEquals("Sharma", b.getLastName());
		}
		
		System.out.println("Fetching all data");
		Iterable<BankAccount> bankAccounts = bankAccountRepository.findAll();
		int count = 0;
		for(BankAccount ba : bankAccounts) {
			System.out.println(ba);
			count++;
		}
		System.out.println("Total Records: " + count);
		assertEquals(count, 2);
	}
}
