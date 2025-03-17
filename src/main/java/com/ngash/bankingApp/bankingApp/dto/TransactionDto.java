package com.ngash.bankingApp.bankingApp.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private String TransactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
}
