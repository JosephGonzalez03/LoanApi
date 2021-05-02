package com.nerd.LoanSystemApi.model.contract;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter @Setter
public class LoanRequestBody {

    @NotBlank
    @NotNull
    private String name;

    @PositiveOrZero
    @Digits(integer=3, fraction=3)
    private BigDecimal interestRate;

    @PositiveOrZero
    @Digits(integer=10, fraction=2)
    private BigDecimal outstandingBalance;

    @PositiveOrZero
    @Digits(integer=10, fraction=2)
    private BigDecimal contribution;

}