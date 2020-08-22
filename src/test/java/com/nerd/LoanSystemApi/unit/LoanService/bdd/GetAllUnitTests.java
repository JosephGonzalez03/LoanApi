package com.nerd.LoanSystemApi.unit.LoanService.bdd;

import com.nerd.LoanSystemApi.model.provider.Loan;
import com.nerd.LoanSystemApi.model.contract.OrderBy;
import com.nerd.LoanSystemApi.model.provider.User;
import com.nerd.LoanSystemApi.repository.LoanDao;
import com.nerd.LoanSystemApi.service.LoanService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetAllUnitTests {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDao;

    User userWithExistentUserId = new User();
    Loan validLoanA = new Loan();
    Loan validLoanB = new Loan();
    List<Loan> actual = new ArrayList<>();
    List<Loan> expected = new ArrayList<>();

    @Before
    public void setup() {
        userWithExistentUserId.setId(1);

        validLoanA.setId(1);
        validLoanA.setName("validLoanA");
        validLoanA.setUser(userWithExistentUserId);
        validLoanA.setInterestRate(BigDecimal.valueOf(5.00));
        validLoanA.setContribution(BigDecimal.TEN);
        validLoanA.setOutstandingBalance(BigDecimal.valueOf(1000.00));

        validLoanB.setId(2);
        validLoanB.setName("validLoanB");
        validLoanB.setUser(userWithExistentUserId);
        validLoanB.setInterestRate(BigDecimal.valueOf(1.00));
        validLoanB.setContribution(BigDecimal.ONE);
        validLoanB.setOutstandingBalance(BigDecimal.valueOf(2000.00));
    }

    @Test
    public void Given_UserWithUserIdHasLoansInDatabase_When_InterestRateOrderByIsUsed_Then_LoanListWillBeReturnedInOrderOfDescendingInterestRates() {
        given(loanDao.findByUserIdOrderByInterestRateDesc(1))
                .willReturn(Arrays.asList(validLoanA, validLoanB));

        actual = loanService.getAll(1, OrderBy.INTEREST_RATE);
        expected = Arrays.asList(validLoanA, validLoanB);

        then(loanDao)
                .should(times(1))
                .findByUserIdOrderByInterestRateDesc(1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Given_UserWithUserIdHasLoansInDatabase_When_OutstandingBalanceOrderByIsUsed_Then_LoanListWillBeReturnedInOrderOfDescendingOutstandingBalances() {
        given(loanDao.findByUserIdOrderByOutstandingBalanceDesc(1))
                .willReturn(Arrays.asList(validLoanA, validLoanB));

        actual = loanService.getAll(1, OrderBy.OUTSTANDING_BALANCE);
        expected = Arrays.asList(validLoanA, validLoanB);

        then(loanDao)
                .should(times(1))
                .findByUserIdOrderByOutstandingBalanceDesc(1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Given_UserWithUserIdHasLoansInDatabase_When_ContributionOrderByIsUsed_Then_LoanListWillBeReturnedInOrderOfDescendingContributions() {
        given(loanDao.findByUserIdOrderByContributionDesc(1))
            .willReturn(Arrays.asList(validLoanA, validLoanB));

        actual = loanService.getAll(1, OrderBy.CONTRIBUTION);
        expected = Arrays.asList(validLoanA, validLoanB);

        then(loanDao)
                .should(times(1))
                .findByUserIdOrderByContributionDesc(1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Given_UserWithUserIdHasLoansInDatabase_When_NameOrderByIsUsed_Then_LoanListWillBeReturnedInAlphabeticalOrder() {
        given(loanDao.findByUserIdOrderByName(1))
                .willReturn(Arrays.asList(validLoanA, validLoanB));

        actual = loanService.getAll(1, OrderBy.NAME);
        expected = Arrays.asList(validLoanA, validLoanB);

        then(loanDao)
                .should(times(1))
                .findByUserIdOrderByName(1);

        Assert.assertEquals(expected, actual);
    }
}
