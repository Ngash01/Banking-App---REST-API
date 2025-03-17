package com.ngash.bankingApp.bankingApp.service;

import com.ngash.bankingApp.bankingApp.entity.Transaction;
import com.ngash.bankingApp.bankingApp.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BankStatement {
//    Retrieve list of transactions in a date range given an account number
//    generate a pdf file of transactions
//    send the file via email

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate){

        List<Transaction> transactionList = transactionRepository.findAll().stream().filter(transaction ->  transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.)
    }

}
