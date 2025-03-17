package com.ngash.bankingApp.bankingApp.service;

import com.ngash.bankingApp.bankingApp.dto.TransactionDto;
import com.ngash.bankingApp.bankingApp.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    public void saveTransaction(TransactionDto transactionDto);


}
