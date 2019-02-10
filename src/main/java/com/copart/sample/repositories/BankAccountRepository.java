package com.copart.sample.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.copart.sample.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

	List<BankAccount> findByFirstName(String firstName);
	List<BankAccount> findByLastName(String lastName);
	List<BankAccount> findByEmail(String email);
}
