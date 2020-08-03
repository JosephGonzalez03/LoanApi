package com.nerd.LoanApi.model.contract;

import com.nerd.LoanApi.model.provider.Loan;
import com.nerd.LoanApi.model.provider.User;
import lombok.*;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter @Setter
public class LoanResponseBody {

    private Integer id;
    private String name;

    @Digits(integer=3, fraction=3)
    private BigDecimal interestRate;

    @Digits(integer=3, fraction=2)
    private BigDecimal outstandingBalance;

    @Digits(integer=3, fraction=2)
    private BigDecimal contribution;

    public LoanResponseBody(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.interestRate = loan.getInterestRate();
        this.outstandingBalance = loan.getOutstandingBalance();
        this.contribution = loan.getContribution();
    }
}