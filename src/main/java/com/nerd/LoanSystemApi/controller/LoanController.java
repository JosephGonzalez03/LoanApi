package com.nerd.LoanSystemApi.controller;

import com.nerd.LoanSystemApi.model.contract.LoanResponseBody;
import com.nerd.LoanSystemApi.model.provider.Loan;
import com.nerd.LoanSystemApi.model.contract.LoanRequestBody;
import com.nerd.LoanSystemApi.model.contract.OrderBy;
import com.nerd.LoanSystemApi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("users/{userId}/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<LoanResponseBody>> getAllLoansByUserIdAndSorted(@PathVariable("userId") Integer userId, @RequestParam OrderBy orderBy) {
        List<Loan> loans = loanService.getAll(userId, orderBy);
        List<LoanResponseBody> loanResponseBodies = new ArrayList<>();

        loans.forEach(loan -> loanResponseBodies.add(new LoanResponseBody(loan)));
        return new ResponseEntity<>(loanResponseBodies, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Void> createLoan(@PathVariable("userId") Integer userId, @Valid @RequestBody LoanRequestBody loanRequestBody) {
        loanService.addLoan(userId, new Loan(userId, loanRequestBody));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{loanId}", produces = "application/json")
    private ResponseEntity<LoanResponseBody> getLoanById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        Loan loan = loanService.getLoan(userId, loanId);
        return new ResponseEntity<>(new LoanResponseBody(loan), HttpStatus.OK);
    }

    @PutMapping(value = "/{loanId}", consumes = "application/json")
    public ResponseEntity<Void> updateLoanById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId, @Valid @RequestBody LoanRequestBody loanRequestBody) {
        loanService.updateLoan(userId, loanId, new Loan(userId, loanRequestBody));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoanById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        loanService.deleteLoan(userId, loanId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
