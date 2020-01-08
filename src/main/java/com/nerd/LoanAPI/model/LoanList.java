package com.nerd.LoanAPI.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class LoanList {
    private List<Loan> loans;
}
