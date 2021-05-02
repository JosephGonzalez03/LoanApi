package com.nerd.LoanSystemApi.unit.LoanService.bdd;

import com.nerd.LoanSystemApi.CustomException.LoanNotFoundException;
import com.nerd.LoanSystemApi.model.provider.Loan;
import com.nerd.LoanSystemApi.model.provider.User;
import com.nerd.LoanSystemApi.repository.LoanDao;
import com.nerd.LoanSystemApi.service.LoanService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateLoanUnitTests {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    User userWithExistentUserId = new User();

    Loan validLoan = new Loan();
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
    public void Given_UserWithUserIdHasLoanWithLoanIdInDatabase_When_UserIdAndLoanIdAreUsed_Then_LoanWillBeUpdatedInDatabase() {
        InOrder inOrder = inOrder(loanDao);

        given(loanDao.findByIdAndUserId(1, 1))
                .willReturn(Optional.of(validLoan));
        given(loanDao.save(validLoan))
                .willReturn(validLoan);

        loanService.updateLoan(1, 1, updatedValidLoan);

        then(loanDao).should(inOrder).findByIdAndUserId(1, 1);
        then(loanDao).should(inOrder).save(validLoan);
    }

    @Test
    public void Given_UserWithUserIdDoesNotHaveLoanWithLoanIdInDatabase_When_UserIdAndLoanIdAreUsed_Then_LoanNotFoundExceptionWillBeThrown() {
        given(loanDao.findByIdAndUserId(100, 1))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> loanService.updateLoan(1,100, updatedValidLoan)).isInstanceOf(LoanNotFoundException.class);

        then(loanDao).should(never()).save(nonexistentValidLoan);
    }
}
