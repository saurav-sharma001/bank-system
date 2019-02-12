package com.copart.sample.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.copart.sample.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

	List<BankAccount> findByIsDeleted(Boolean isDeleted);
	Optional<BankAccount> findByAccountIdAndIsDeleted(Long id, Boolean isDeleted);
	List<BankAccount> findByEmail(String email);
	List<BankAccount> findByLastName(String lastName);
}
