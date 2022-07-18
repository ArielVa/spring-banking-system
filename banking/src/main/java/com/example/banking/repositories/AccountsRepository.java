package com.example.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.banking.entities.account.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {

}
