package com.nerd.LoanApi.model.contract;

import lombok.*;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter @Setter
public class LoanRequestBody {

    private String name;

    @Digits(integer=3, fraction=3)
    private BigDecimal interestRate;

    @Digits(integer=3, fraction=2)
    private BigDecimal outstandingBalance;

    @Digits(integer=3, fraction=2)
    private BigDecimal contribution;

}