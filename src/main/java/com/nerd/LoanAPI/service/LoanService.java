package com.nerd.LoanAPI.service;

import com.nerd.LoanAPI.CustomException.LoanNotFoundException;
import com.nerd.LoanAPI.CustomException.UserNotFoundException;
import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.OrderBy;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.repository.UserDao;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private LoanDao loanDao;

    public List<Loan> getAll(Integer userId, OrderBy orderBy) {
        List<Loan> loans = null;

        switch (orderBy) {
            case INTEREST_RATE:
                loans = loanDao.findByUserIdOrderByInterestRateDesc(userId);
                break;
            case OUTSTANDING_BALANCE:
                loans = loanDao.findByUserIdOrderByOutstandingBalanceDesc(userId);
                break;
            case CONTRIBUTION:
                loans = loanDao.findByUserIdOrderByContributionDesc(userId);
                break;
            case NAME:
                loans = loanDao.findByUserIdOrderByName(userId);
                break;
        }
        return loans;
    }

    public Loan getLoan(Integer userId, Integer loanId) {
        return loanDao.findByIdAndUserId(loanId, userId).orElseThrow(LoanNotFoundException::new);
    }

    public void addLoan(Integer userId, @NonNull Loan loan) {
        loan.setUser(userDao.findById(userId).orElseThrow(UserNotFoundException::new));
        loanDao.save(loan);
    }

    public void updateLoan(Integer userId, Integer loanId, @NonNull Loan loan) {
        Loan mLoan = getLoan(userId, loanId);

        mLoan.setName(loan.getName());
        mLoan.setInterestRate(loan.getInterestRate());
        mLoan.setOutstandingBalance(loan.getOutstandingBalance());
        mLoan.setContribution(loan.getContribution());
        loanDao.save(mLoan);
    }

    public void deleteLoan(Integer userId, Integer loanId) {
        loanDao.delete(getLoan(userId, loanId));
    }
}
