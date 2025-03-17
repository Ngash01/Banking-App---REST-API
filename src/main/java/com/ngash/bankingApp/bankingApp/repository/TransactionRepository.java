package com.ngash.bankingApp.bankingApp.repository;


import com.ngash.bankingApp.bankingApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
