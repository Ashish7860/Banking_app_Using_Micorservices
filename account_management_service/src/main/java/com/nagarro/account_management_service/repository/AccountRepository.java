package com.nagarro.account_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.account_management_service.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Add custom repository methods if needed
}
