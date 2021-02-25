package com.nerd.LoanSystemApi.model.provider;

import com.nerd.LoanSystemApi.model.contract.LoanRequestBody;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="loans")
public class Loan implements Serializable {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Digits(integer=3, fraction=3)
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Digits(integer=10, fraction=2)
    @Column(name = "outstanding_balance")
    private BigDecimal outstandingBalance;

    @Digits(integer=10, fraction=2)
    @Column(name = "contribution")
    private BigDecimal contribution;

    public Loan (Integer userId, LoanRequestBody loanRequestBody) {
        this.user = new User();
        this.user.setId(userId);
        this.name = loanRequestBody.getName();
        this.interestRate = loanRequestBody.getInterestRate();
        this.outstandingBalance = loanRequestBody.getOutstandingBalance();
        this.contribution = loanRequestBody.getContribution();
    }
}