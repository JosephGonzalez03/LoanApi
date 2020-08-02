package com.nerd.LoanApi.model.provider;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class User implements Serializable {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Loan> loans = new ArrayList<>();

    public User() {
    }

    public Loan getLoan(Integer loanId) {
        return getUserLoanById(loanId);
    }

    public void addLoan(Loan loan) {
        loan.setUser(this);
        loans.add(loan);
    }

    public void updateLoan(Integer loanId, Loan loan) {
        Loan mLoan = getUserLoanById(loanId);

        mLoan.setName(loan.getName());
        mLoan.setInterestRate(loan.getInterestRate());
        mLoan.setOutstandingBalance(loan.getOutstandingBalance());
        mLoan.setContribution(loan.getContribution());
    }

    public void deleteLoan(Integer loanId) {
        loans.remove(getUserLoanById(loanId));
    }

    private Loan getUserLoanById(Integer loanId) {
        Loan mLoan = new Loan();
        mLoan.setId(loanId);

        return loans.get(loans.indexOf(mLoan));
    }
}
