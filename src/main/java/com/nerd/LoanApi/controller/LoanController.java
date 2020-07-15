package com.nerd.LoanApi.controller;

import com.nerd.LoanApi.model.Loan;
import com.nerd.LoanApi.model.LoanList;
import com.nerd.LoanApi.model.OrderBy;
import com.nerd.LoanApi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users/{userId}/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("")
    public LoanList getAllByUserIdAndSorted(@PathVariable("userId") Integer userId, @RequestParam String orderBy) {
        LoanList loanList = new LoanList();

        loanList.setLoans(loanService.getAll(userId, OrderBy.valueOf(orderBy.toUpperCase())));
        return loanList;
    }

    @PostMapping("")
    public void createLoan(@PathVariable("userId") Integer userId, @RequestBody Loan loan) {
        loanService.addLoan(userId, loan);
    }

    @GetMapping("/{loanId}")
    private Loan getById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        return loanService.getLoan(userId, loanId);
    }

    @PutMapping("/{loanId}")
    public void updateById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId, @RequestBody Loan loan) {
        loanService.updateLoan(userId, loanId, loan);
    }

    @DeleteMapping("/{loanId}")
    public void deleteById(@PathVariable("userId") Integer userId, @PathVariable("loanId") Integer loanId) {
        loanService.deleteLoan(userId, loanId);
    }
}
