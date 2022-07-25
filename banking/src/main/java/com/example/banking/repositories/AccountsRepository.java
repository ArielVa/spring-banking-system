package com.example.banking.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.banking.entities.Loan;
import com.example.banking.entities.account.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
	
	@Query(value = "select * "
			+ " from accounts"
			+ " left join customers"
			+ " on accounts.customer_id = customers.customer_id "
			+ " where customers.is_deleted = false and accounts.is_suspended = false and accounts.account_num = :accountNum"
			,nativeQuery = true)
	Account getAnActiveAccountByNum(@Param("accountNum")Integer accountNum);
	
	List<Account> findAllByIsSuspendedFalse();

}
