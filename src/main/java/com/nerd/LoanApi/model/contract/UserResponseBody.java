package com.nerd.LoanApi.model.contract;

import com.nerd.LoanApi.model.provider.Loan;
import com.nerd.LoanApi.model.provider.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserResponseBody {

    private Integer id;
    private String firstName;
    private String lastName;
    List<LoanResponseBody> loans = new ArrayList<>();

    public UserResponseBody(User user) {
        List<LoanResponseBody> loanResponseBodies = new ArrayList<>();

        this.id = user.getId();
        this.firstName = user.getLastName();
        this.lastName = user.getLastName();
        user.getLoans().forEach(loan -> loanResponseBodies.add(new LoanResponseBody(loan)));
        this.loans = loanResponseBodies;
    }

}
