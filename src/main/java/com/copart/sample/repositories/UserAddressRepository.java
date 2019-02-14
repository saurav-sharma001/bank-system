package com.copart.sample.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.copart.sample.model.BankAccount;
import com.copart.sample.model.UserAddress;

public interface UserAddressRepository extends CrudRepository<UserAddress, Long> {

	Optional<BankAccount> findByAccountNumber(Long accountNumber);

}
