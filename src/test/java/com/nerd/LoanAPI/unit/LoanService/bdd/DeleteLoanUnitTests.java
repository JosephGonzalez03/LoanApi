package com.nerd.LoanAPI.unit.LoanService.bdd;

import com.nerd.LoanAPI.CustomException.LoanNotFoundException;
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
public class DeleteLoanUnitTests {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    @Mock
    private UserDao userDao;

    User userWithExistentUserId = new User();
    Loan existentLoan = new Loan();

    @Before
    public void setup() {
        userWithExistentUserId.setId(1);

        existentLoan.setId(1);
        existentLoan.setName("existentLoan");
        existentLoan.setUser(userWithExistentUserId);
        existentLoan.setInterestRate(BigDecimal.valueOf(5.00));
        existentLoan.setContribution(BigDecimal.TEN);
        existentLoan.setOutstandingBalance(BigDecimal.valueOf(1000.00));
    }

    @Test
    public void Given_LoanWithLoanIdExistsInDatabase_When_LoanWithLoanIdIsDeleted_Then_LoanWillBeDeletedFromDatabase() {
        given(loanDao.findByIdAndUserId(1, 1))
                .willReturn(Optional.of(existentLoan));
        doNothing().when(loanDao).delete(existentLoan);

        loanService.deleteLoan(1, 1);

        then(loanDao)
                .should(times(1))
                .delete(existentLoan);
    }

    @Test
    public void Given_LoanWithLoanIdDoesNotExistInDatabase_When_LoanWithLoanIdIsDeleted_Then_LoanNotFoundExceptionWillBeThrown() {
        given(loanDao.findByIdAndUserId(1, 1))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> loanService.deleteLoan(1, 1)).isInstanceOf(LoanNotFoundException.class);

        then(loanDao)
                .should(never())
                .delete(existentLoan);
    }
}
