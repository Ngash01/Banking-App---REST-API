package com.ngash.bankingApp.bankingApp.utils;

import java.time.Month;
import java.time.Year;
import java.util.Random;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "401";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account successfully created!";
    public static final String ACCOUNT_NOT_EXISTS_CODE = "404";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "This account does not exist in the system";
    public static final String ACCOUNT_CREDITED_SUCCESS_CODE = "200";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Account credited successfully";
    public static final String ACCOUNT_BALANCE_LOW_MESSAGE = "Insufficient funds to perform this action";
    public static final String ACCOUNT_BALANCE_LOW_CODE = "405";
    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "200";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Account debited successfully";


    public static String generateAccountNumber(){

        Random random = new Random();

        Year currentYear = Year.now();
        int randomNum = random.nextInt(500000, 999999);

        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append(currentYear).append(randomNum).toString();
    }
}
