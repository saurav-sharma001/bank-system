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
import org.springframework.test.context.junit4.SpringRunner;

import com.copart.sample.model.BankAccount;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankAccountRepositoryTest {

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Before
	public void setUp() throws Exception {
		
		System.out.println("---------- Running Test Cases -----------");
		BankAccount ba1 = new BankAccount("Saurav", "Sharma", "Saurav.Sharma@Copart.com", null);
		BankAccount ba2 = new BankAccount("Vishrut", "Sharma", "Gaurav.Sharma@Copart.com", null);
		
		assertNull(ba1.getAccountId());
		assertNull(ba2.getAccountId());
		
		this.bankAccountRepository.save(ba1);
		this.bankAccountRepository.save(ba2);
		
		assertNotNull(ba1.getAccountId());
		assertNotNull(ba2.getAccountId());
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
