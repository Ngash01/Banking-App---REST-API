package com.ngash.bankingApp.bankingApp.service;

import com.ngash.bankingApp.bankingApp.dto.*;
import com.ngash.bankingApp.bankingApp.entity.User;
import com.ngash.bankingApp.bankingApp.repository.UserRepository;
import com.ngash.bankingApp.bankingApp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())){
           return  BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();

        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .status("ACTIVE")
                .build();


        User savedUser = userRepository.save(user);

//        Send email alerts
         emailService.sendEmailAlert(new EmailDetails(
                savedUser.getEmail(),
                "Congratulations your account has successfully been created\n. Your account details: \n" +
                        "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\n Account Number: " + savedUser.getAccountNumber(),
                "ACCOUNT CREATION SUCCESS",
                AccountUtils.ACCOUNT_CREATION_MESSAGE));

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .build())
                .build();
    }

//        balance enquiry, name enquiry, credit, debit, transfer,

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
//
        if(userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber())){

            User user = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage("Balance" )
                    .accountInfo(AccountInfo.builder()
                            .accountName(user.getFirstName() + " " +  user.getLastName())
                            .accountBalance(user.getAccountBalance())
                            .accountNumber(user.getAccountNumber())
                            .build())
                    .build();

        }else{
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
    }

    @Override
    public String getNameEnquiry(String account) {

        if(!userRepository.existsByAccountNumber(account)){
            BankResponse bankResponse =  BankResponse.builder()
                    .responseMessage("This account number does not exist in the system")
                    .build();

            return bankResponse.getResponseMessage();

        }else{
            User user = userRepository.findByAccountNumber(account);

            BankResponse bankResponse = BankResponse.builder()
                  .accountInfo(AccountInfo.builder()
                          .accountName(user.getFirstName() + " " + user.getLastName()).build())
                    .build();

            return bankResponse.getAccountInfo().getAccountName();

        }
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        if(userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber())){

            User userToCredit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());

            userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));

            userRepository.save(userToCredit);

//            save transaction
            TransactionDto transactionDto = TransactionDto.builder()
                    .accountNumber(userToCredit.getAccountNumber())
                    .TransactionType("CREDIT")
                    .amount(creditDebitRequest.getAmount())
                    .build();

            transactionService.saveTransaction(transactionDto);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                            .accountNumber(userToCredit.getAccountNumber())
                            .accountBalance(userToCredit.getAccountBalance()).build())
                    .build();

        }else{
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
//        check if the account exists
//        check if the amount you intend to withdraw is not more than the current account balance

        if(userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber())){

            User userToDebit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());

//            save transaction
            TransactionDto transactionDto = TransactionDto.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .TransactionType("DEBIT")
                    .amount(creditDebitRequest.getAmount())
                    .build();

            transactionService.saveTransaction(transactionDto);

            if(creditDebitRequest.getAmount().compareTo(userToDebit.getAccountBalance()) > 0){
                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_BALANCE_LOW_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_BALANCE_LOW_MESSAGE)
                        .accountInfo(AccountInfo.builder()
                                .accountNumber(userToDebit.getAccountNumber())
                                .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                                .accountBalance(userToDebit.getAccountBalance())
                                .build())
                        .build();
            }else{
                userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
                userRepository.save(userToDebit);

                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                        .accountInfo(AccountInfo.builder()
                                .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                                .accountNumber(userToDebit.getAccountNumber())
                                .accountBalance(userToDebit.getAccountBalance())
                                .build())
                        .build();
            }

        }else {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
    }

    @Override
    public BankResponse transferRequest(TransferRequest transferRequest) {
//        get the account to debit(check if it exists)
//        check that the amount to be debited is not more than the current balance
//        debit the account
//        get the account to credit
//        credit the account

        if(userRepository.existsByAccountNumber(transferRequest.getSourceAccountNumber()) && userRepository.existsByAccountNumber(transferRequest.getDestinationAccount())){

            User userToDebit = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
            User userToCredit = userRepository.findByAccountNumber(transferRequest.getDestinationAccount());

            if(transferRequest.getAmount().compareTo(userToDebit.getAccountBalance()) > 0){
                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_BALANCE_LOW_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_BALANCE_LOW_MESSAGE)
                        .accountInfo(AccountInfo.builder()
                                .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                                .accountNumber(userToDebit.getAccountNumber())
                                .accountBalance(userToDebit.getAccountBalance())
                                .build())
                        .build();
            }else{
//              Debit
                userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(transferRequest.getAmount()));
                userRepository.save(userToDebit);

//             Send email alerts
                emailService.sendEmailAlert(new EmailDetails(
                        userToDebit.getEmail(),
                        "A withdrawal request has been made to your account ending with 503. If you don't recognize this activity reset your password and contact support\n" +
                                "Account Name: " + userToDebit.getFirstName() + " " + userToDebit.getLastName() + "\n Account Number: " + userToDebit.getAccountNumber() + "\n" +
                                "Withdrawal amount: $" + transferRequest.getAmount(),
                        "ALERT! WITHDRAWAL REQUEST",
                        AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE));

                BankResponse debitedAccount = BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                        .accountInfo(AccountInfo.builder()
                                .accountName(userToDebit.getFirstName() + " "  + userToDebit.getLastName())
                                .accountNumber(userToDebit.getAccountNumber())
                                .accountBalance(userToDebit.getAccountBalance())
                                .build()).build();

//                credit
                userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(transferRequest.getAmount()));
                userRepository.save(userToCredit);

                BankResponse creditAccount = BankResponse.builder()
                        .responseCode((AccountUtils.ACCOUNT_CREATION_SUCCESS))
                        .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                        .accountInfo(AccountInfo.builder()
                                .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                                .accountNumber(userToCredit.getAccountNumber())
                                .accountBalance(userToCredit.getAccountBalance()
                                ).build())
                        .build();

                return debitedAccount;

            }

        }else{
            return  BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

    }


}

