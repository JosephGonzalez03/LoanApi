package com.nerd.LoanApi.controller;

import com.nerd.LoanApi.model.contract.LoanResponseBody;
import com.nerd.LoanApi.model.provider.Loan;
import com.nerd.LoanApi.model.contract.LoanRequestBody;
import com.nerd.LoanApi.model.contract.OrderBy;
import com.nerd.LoanApi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "users/{userId}/loans", consumes = "application/json", produces = "application/json")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("")
    public ResponseEntity<List<LoanResponseBody>> getAllByUserIdAndSorted(@PathVariable("userId") Integer userId, @RequestParam OrderBy orderBy) {
        List<Loan> loans = loanService.getAll(userId, orderBy);
        List<LoanResponseBody> loanResponseBodies = new ArrayList<>();

        loans.forEach(loan -> loanResponseBodies.add(new LoanResponseBody(loan)));
        return new ResponseEntity<>(loanResponseBodies, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity createLoan(@PathVariable("userId") Integer userId, @RequestBody LoanRequestBody loanRequestBody) {
        loanService.addLoan(userId, new Loan(userId, loanRequestBody));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{loanId}")
    private ResponseEntity<LoanResponseBody> getById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        Loan loan = loanService.getLoan(userId, loanId);
        return new ResponseEntity<>(new LoanResponseBody(loan), HttpStatus.OK);
    }

    @PutMapping("/{loanId}")
    public ResponseEntity updateById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId, @RequestBody LoanRequestBody loanRequestBody) {
        loanService.updateLoan(userId, loanId, new Loan(userId, loanRequestBody));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity deleteById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        loanService.deleteLoan(userId, loanId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
