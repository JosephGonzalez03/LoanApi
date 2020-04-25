package com.nerd.LoanAPI.unit.LoanService;

import com.nerd.LoanAPI.CustomException.LoanNotFoundException;
import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.service.LoanService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class UpdateLoanUnitTests {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    User userWithExistentUserId = new User();

    @Spy
    Loan validLoan = new Loan();

    @Spy
    Loan nonexistentValidLoan = new Loan();

    Loan updatedValidLoan = new Loan();

    @Before
    public void setup() {
        validLoan.setId(1);
        validLoan.setName("Home Loan 1");
        validLoan.setUser(userWithExistentUserId);
        validLoan.setInterestRate(BigDecimal.valueOf(5.00));
        validLoan.setContribution(BigDecimal.TEN);
        validLoan.setOutstandingBalance(BigDecimal.valueOf(1000.00));

        nonexistentValidLoan.setId(100);
        nonexistentValidLoan.setName("Home Loan 1");
        nonexistentValidLoan.setUser(userWithExistentUserId);
        nonexistentValidLoan.setInterestRate(BigDecimal.valueOf(5.00));
        nonexistentValidLoan.setContribution(BigDecimal.TEN);
        nonexistentValidLoan.setOutstandingBalance(BigDecimal.valueOf(1000.00));

        updatedValidLoan.setName("Home Loan 2");
        updatedValidLoan.setInterestRate(BigDecimal.valueOf(10.00));
        updatedValidLoan.setContribution(BigDecimal.ONE);
        updatedValidLoan.setOutstandingBalance(BigDecimal.valueOf(2000.00));
    }

    @Test
    public void updateLoan_ExistentUserIdAs1stParam_ExistentLoanIdAs2ndParam_ValidLoanAs3rdParam_ReturnsNothing() {
        when(loanDao.findByIdAndUserId(1,1))
                .thenReturn(Optional.of(validLoan));
        when(loanDao.save(validLoan))
                .thenReturn(validLoan);

        loanService.updateLoan(1,1, updatedValidLoan);

        verify(validLoan, times(1)).setName(updatedValidLoan.getName());
        verify(validLoan, times(1)).setInterestRate(updatedValidLoan.getInterestRate());
        verify(validLoan, times(1)).setOutstandingBalance(updatedValidLoan.getOutstandingBalance());
        verify(validLoan, times(1)).setContribution(updatedValidLoan.getContribution());
        verify(loanDao, times(1)).save(validLoan);
    }

    @Test
    public void updateLoan_ExistentUserIdAs1stParam_NonexistentLoanIdAs2ndParam_ValidLoanAs3rdParam_ReturnsLoanNotFoundException() {
        when(loanDao.findByIdAndUserId(100,1))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> loanService.updateLoan(1,100, updatedValidLoan)).isInstanceOf(LoanNotFoundException.class);
        verify(loanDao, times(1)).findByIdAndUserId(100,1);
        verify(nonexistentValidLoan, never()).setName(updatedValidLoan.getName());
        verify(nonexistentValidLoan, never()).setInterestRate(updatedValidLoan.getInterestRate());
        verify(nonexistentValidLoan, never()).setOutstandingBalance(updatedValidLoan.getOutstandingBalance());
        verify(nonexistentValidLoan, never()).setContribution(updatedValidLoan.getContribution());
        verify(loanDao, never()).save(nonexistentValidLoan);
    }

}
