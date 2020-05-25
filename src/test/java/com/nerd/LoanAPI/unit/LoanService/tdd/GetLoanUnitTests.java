package com.nerd.LoanAPI.unit.LoanService.tdd;

import com.nerd.LoanAPI.CustomException.LoanNotFoundException;
import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.service.LoanService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class GetLoanUnitTests {
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
    public void getLoan_ExistentUserIdAs1stParam_ExistentLoanIdAs2ndParam_ReturnsLoanWithCorrespondingIds() {
        when(loanDao.findByIdAndUserId(1,1))
                .thenReturn(Optional.of(validLoan));

        Loan found = loanService.getLoan(1,1);

        Assert.assertEquals(validLoan, found);
        verify(loanDao, times(1)).findByIdAndUserId(1,1);
    }

    @Test
    public void getLoan_ExistentUserIdAs1stParam_NonexistentLoanIdAs2ndParams_ReturnsLoanNotFoundException() {
        when(loanDao.findByIdAndUserId(5,1))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> loanService.getLoan(1,5)).isInstanceOf(LoanNotFoundException.class);
        verify(loanDao, times(1)).findByIdAndUserId(5,1);
    }
}
