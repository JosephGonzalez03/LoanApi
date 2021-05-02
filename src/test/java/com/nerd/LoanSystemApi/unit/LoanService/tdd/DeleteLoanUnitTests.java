package com.nerd.LoanSystemApi.unit.LoanService.tdd;

import com.nerd.LoanSystemApi.CustomException.LoanNotFoundException;
import com.nerd.LoanSystemApi.model.provider.Loan;
import com.nerd.LoanSystemApi.model.provider.User;
import com.nerd.LoanSystemApi.repository.LoanDao;
import com.nerd.LoanSystemApi.service.LoanService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteLoanUnitTests {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    User userWithExistentUserId = new User();
    Loan validLoan = new Loan();

    @Before
    public void setup() {
        userWithExistentUserId.setId(1);

        validLoan.setId(1);
        validLoan.setName("validLoan");
        validLoan.setUser(userWithExistentUserId);
        validLoan.setInterestRate(BigDecimal.valueOf(5.00));
        validLoan.setContribution(BigDecimal.TEN);
        validLoan.setOutstandingBalance(BigDecimal.valueOf(1000.00));
    }

    @Test
    public void deleteLoan_ExistentUserIdAs1stParam_ExistentLoanIdAs2ndParam_ReturnsNothing() {
        when(loanDao.findByIdAndUserId(1,1))
                .thenReturn(Optional.of(validLoan));
        doNothing().when(loanDao).delete(validLoan);

        loanService.deleteLoan(1,1);

        verify(loanDao, times(1)).findByIdAndUserId(1,1);
        verify(loanDao, times(1)).delete(validLoan);
    }

    @Test
    public void deleteLoan_ExistentUserIdAs1stParam_NonexistentLoanIdAs2ndParam_ReturnsLoanNotFoundException() {
        when(loanDao.findByIdAndUserId(1,1))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> loanService.deleteLoan(1,1)).isInstanceOf(LoanNotFoundException.class);
        verify(loanDao, times(1)).findByIdAndUserId(1,1);
        verify(loanDao, never()).delete(validLoan);
    }
}
