package com.ngash.bankingApp.bankingApp.service;

import com.ngash.bankingApp.bankingApp.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public BankResponse createAccount(UserRequest userRequest);
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    public String getNameEnquiry(String account);
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
    public BankResponse transferRequest(TransferRequest transferRequest);

}
