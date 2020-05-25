package com.nerd.LoanAPI.unit.LoanService.bdd;

import com.nerd.LoanAPI.CustomException.UserNotFoundException;
import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.repository.UserDao;
import com.nerd.LoanAPI.service.LoanService;
import org.assertj.core.api.Assertions;
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
public class AddLoanUnitTests {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    @Mock
    private UserDao userDao;

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
    public void Given_LoanIsValid_When_ExistentUserIdIsUsed_Then_LoanWillBeSavedInDatabase() {
        given(userDao.findById(1))
                .willReturn(Optional.of(userWithExistentUserId));
        given(loanDao.save(validLoan))
                .willReturn(validLoan);

        loanService.addLoan(1, validLoan);

        then(loanDao)
                .should(times(1))
                .save(validLoan);
    }

    @Test
    public void Given_LoanIsValid_When_NonExistentUserIdIsUsed_Then_UserNotFoundExceptionWillBeThrown() {
        given(userDao.findById(1))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> loanService.addLoan(1, validLoan)).isInstanceOf(UserNotFoundException.class);

        then(loanDao).shouldHaveZeroInteractions();
    }
}
