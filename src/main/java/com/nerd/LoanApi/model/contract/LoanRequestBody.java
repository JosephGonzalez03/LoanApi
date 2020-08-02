package com.nerd.LoanApi.model.contract;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter @Setter
public class LoanRequestBody {

    private String name;
    private BigDecimal interestRate;
    private BigDecimal outstandingBalance;
    private BigDecimal contribution;

}