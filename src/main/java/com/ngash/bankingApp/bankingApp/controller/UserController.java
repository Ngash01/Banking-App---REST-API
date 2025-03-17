package com.ngash.bankingApp.bankingApp.controller;

import com.ngash.bankingApp.bankingApp.dto.*;
import com.ngash.bankingApp.bankingApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name="User Management APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Create new User Account",
            description = "Creating a new user and assigning an account ID"
    )
    @PostMapping("/create_account")
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceInfo")
    public BankResponse balanceInfo(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameInfo")
    public String getNameEnquiry(@RequestParam(name = "account") String account){
        return userService.getNameEnquiry(account);
    }

    @PostMapping("/credit_account")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.creditAccount(creditDebitRequest);
    }

    @PostMapping("/debit_account")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.debitAccount(creditDebitRequest);
    }

    @PostMapping("/transfer_request")
    public BankResponse transferRequest(@RequestBody TransferRequest transferRequest){
        return userService.transferRequest(transferRequest);
    }
}


