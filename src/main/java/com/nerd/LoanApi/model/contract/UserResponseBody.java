package com.nerd.LoanApi.model.contract;

import com.nerd.LoanApi.model.provider.Loan;
import com.nerd.LoanApi.model.provider.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserResponseBody {

    private Integer id;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotNull
    List<LoanResponseBody> loans = new ArrayList<>();

    public UserResponseBody(User user) {
        List<LoanResponseBody> loanResponseBodies = new ArrayList<>();

        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        user.getLoans().forEach(loan -> loanResponseBodies.add(new LoanResponseBody(loan)));
        this.loans = loanResponseBodies;
    }

}
