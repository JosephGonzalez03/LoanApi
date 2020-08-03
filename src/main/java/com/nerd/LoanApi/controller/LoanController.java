package com.nerd.LoanApi.controller;

import com.nerd.LoanApi.model.contract.LoanResponseBody;
import com.nerd.LoanApi.model.provider.Loan;
import com.nerd.LoanApi.model.contract.LoanRequestBody;
import com.nerd.LoanApi.model.contract.OrderBy;
import com.nerd.LoanApi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "users/{userId}/loans", consumes = "application/json", produces = "application/json")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("")
    public List<LoanResponseBody> getAllByUserIdAndSorted(@PathVariable("userId") Integer userId, @RequestParam String orderBy) {
        List<Loan> loans = loanService.getAll(userId, OrderBy.valueOf(orderBy.toUpperCase()));
        List<LoanResponseBody> loanResponseBodies = new ArrayList<>();

        loans.forEach(loan -> loanResponseBodies.add(new LoanResponseBody(loan)));
        return loanResponseBodies;
    }

    @PostMapping("")
    public void createLoan(@PathVariable("userId") Integer userId, @RequestBody LoanRequestBody loanRequestBody) {
        loanService.addLoan(userId, new Loan(userId, loanRequestBody));
    }

    @GetMapping("/{loanId}")
    private LoanResponseBody getById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        Loan loan = loanService.getLoan(userId, loanId);
        return new LoanResponseBody(loan);
    }

    @PutMapping("/{loanId}")
    public void updateById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId, @RequestBody LoanRequestBody loanRequestBody) {
        loanService.updateLoan(userId, loanId, new Loan(userId, loanRequestBody));
    }

    @DeleteMapping("/{loanId}")
    public void deleteById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        loanService.deleteLoan(userId, loanId);
    }
}
