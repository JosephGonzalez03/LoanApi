package com.nerd.LoanAPI.unit.LoanService.bdd;

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

import static org.mockito.BDDMockito.*;

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
    public void Given_UserWithUserIdHasLoanWithLoanIdInDatabase_When_UserIdAndLoanIdAreUsed_Then_LoanWithUserIdAndLoanIdWillBeReturned() {
        given(loanDao.findByIdAndUserId(1, 1))
                .willReturn(Optional.of(validLoan));

        Loan actual = loanService.getLoan(1, 1);

        then(loanDao)
                .should(times(1))
                .findByIdAndUserId(1, 1);

        Assert.assertEquals(validLoan, actual);
    }

    @Test
    public void Given_UserWithUserIdDoesNotHaveLoanWithLoanIdInDatabase_When_UserIdAndLoanIdAreUsed_Then_LoanNotFoundExceptionWillBeThrown() {
        given(loanDao.findByIdAndUserId(5, 1))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> loanService.getLoan(1, 5)).isInstanceOf(LoanNotFoundException.class);

        then(loanDao)
                .should(times(1))
                .findByIdAndUserId(5, 1);
    }
}
